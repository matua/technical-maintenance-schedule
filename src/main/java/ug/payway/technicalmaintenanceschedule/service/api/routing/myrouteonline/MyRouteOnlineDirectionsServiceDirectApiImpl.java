package ug.payway.technicalmaintenanceschedule.service.api.routing.myrouteonline;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.model.LatLng;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ug.payway.technicalmaintenanceschedule.dto.myrouteonline.*;
import ug.payway.technicalmaintenanceschedule.exception.NotFoundException;
import ug.payway.technicalmaintenanceschedule.exception.ValidationException;
import ug.payway.technicalmaintenanceschedule.model.Schedule;
import ug.payway.technicalmaintenanceschedule.model.TaskPriority;
import ug.payway.technicalmaintenanceschedule.model.Terminal;
import ug.payway.technicalmaintenanceschedule.model.User;
import ug.payway.technicalmaintenanceschedule.service.ScheduleService;
import ug.payway.technicalmaintenanceschedule.service.UserService;
import ug.payway.technicalmaintenanceschedule.service.api.routing.DirectionsService;

import java.util.*;
import java.util.concurrent.TimeUnit;

@org.springframework.stereotype.Service("myrouteonline")
@Slf4j
@RequiredArgsConstructor
public class MyRouteOnlineDirectionsServiceDirectApiImpl implements DirectionsService {

  private static final String HEAD_OFFICE = "Head Office";
  private final RestTemplate restTemplate;
  private final ObjectMapper objectMapper;
  private final ScheduleService scheduleService;
  private final UserService userService;
  private final ModelMapper modelMapper;
  //    private final UserService userService;
  @Value("${myrouteonline.api.key}")
  private String myRouteOnlineApiKey;

  @Value("${startTime}")
  private String startTime;

  @Value("${visitClosestFirst}")
  private String visitClosestFirst;

  @Value("${isFixedNumberOfRoutes}")
  private String isFixedNumberOfRoutes;

  @Value("${fixedNumberOfRoutes}")
  private String fixedNumberOfRoutes;

  @Value("${serviceTimeInMinutes}")
  private String serviceTimeInMinutes;

  @Override
  public Optional<List<Terminal>> getOptimalRouteListOfTerminals(
      List<Terminal> origins, List<Terminal> destinations, List<Terminal> terminalLocations) {
    return Optional.empty();
  }

  @Override
  public Optional<List<Terminal>> getOptimalRouteListOfTerminalsWithLatLngStartAndFinishPoint(
      LatLng startAndFinishPoint, List<Terminal> terminalLocations) {
    return Optional.empty();
  }

  @Override
  public Optional<int[]> getOptimalIndicesOfOrderOfTerminals(
      List<Terminal> origins, List<Terminal> destinations, List<Terminal> terminalLocations) {
    return Optional.empty();
  }

  @Override
  public Optional<List<Schedule>> getOptimalIndicesOfOrderOfSchedules(
      List<Schedule> schedules,
      List<User> users,
      Double startAddressLatitude,
      Double startAddressLongitude,
      Double endAddressLatitude,
      Double endAddressLongitude) {
    log.info("Building a jobTokenRequest object for MyRouteOnline API..");

    final RoutingParameters routingParameters =
        RoutingParameters.builder()
            .startTime(startTime)
            .visitClosestFirst(Boolean.valueOf(visitClosestFirst))
            .build();

    final SpecificRouteConstraint specificRouteConstraint =
        SpecificRouteConstraint.builder()
            .startAt(
                Address.builder()
                    .idNumber(0)
                    .title(HEAD_OFFICE)
                    .serviceTimeInMinutes(0)
                    .address("[" + startAddressLatitude + ", " + startAddressLongitude + "]")
                    .build())
            .endAt(
                Address.builder()
                    .idNumber(0)
                    .title(HEAD_OFFICE)
                    .serviceTimeInMinutes(0)
                    .address("[" + endAddressLatitude + ", " + endAddressLongitude + "]")
                    .build())
            .build();

    final RoutesConstraints routesConstraints =
        RoutesConstraints.builder()
            .isFixedNumberOfRoutes(Boolean.valueOf(isFixedNumberOfRoutes))
            .fixedNumberOfRoutes(users.size())
            .specificRouteConstraints(List.of(specificRouteConstraint))
            .build();

    List<Address> addresses = new ArrayList<>();
    schedules.forEach(
        schedule ->
            addresses.add(
                Address.builder()
                    .idNumber(Math.toIntExact(schedule.getId()))
                    .title(
                        schedule.getTerminal().getName()
                            + "|"
                            + schedule.getTask().getDescription())
                    .serviceTimeInMinutes(Integer.valueOf(serviceTimeInMinutes))
                    .address(
                        "["
                            + schedule.getTerminal().getLatitude()
                            + ", "
                            + schedule.getTerminal().getLongitude()
                            + "]")
                    .build()));

    RouteOptimizationRequest routeOptimizationRequest =
        RouteOptimizationRequest.builder()
            .routingParameters(routingParameters)
            .routesConstraints(routesConstraints)
            .addresses(addresses)
            .build();

    UriComponents routePlanStartUri =
        UriComponentsBuilder.newInstance()
            .scheme("https")
            .host("planner.myrouteonline.com")
            .pathSegment("ws_api/")
            .queryParam("m", "routePlanStart")
            .build();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
    parameters.add("apiToken", myRouteOnlineApiKey);
    try {
      parameters.add("inRequest", objectMapper.writeValueAsString(routeOptimizationRequest));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    HttpEntity<MultiValueMap<String, String>> jobTokenRequest =
        new HttpEntity<>(parameters, headers);

    log.info(
        "Shooting at MyRouteOnline API to get the job token...\n{}",
        routePlanStartUri.toUriString());

    ResponseEntity<JobResponse> jobResponseEntity =
        restTemplate.postForEntity(
            routePlanStartUri.toUriString(), jobTokenRequest, JobResponse.class);

    final JobResponse body = jobResponseEntity.getBody();

    log.info(String.valueOf(body));

    List<Schedule> optimizedSchedules = new ArrayList<>();

    if (Boolean.TRUE.equals(body.isSuccessful)) {
      String jobToken = body.jobToken;

      UriComponents routePlanCheckUri =
          UriComponentsBuilder.newInstance()
              .scheme("https")
              .host("planner.myrouteonline.com")
              .pathSegment("ws_api/")
              .queryParam("m", "routePlanCheck")
              .build();

      parameters = new LinkedMultiValueMap<>();
      parameters.add("apiToken", myRouteOnlineApiKey);
      parameters.add("jobToken", jobToken);

      HttpEntity<MultiValueMap<String, String>> mainRequest = new HttpEntity<>(parameters, headers);

      log.info(
          "Shooting at MyRouteOnline API to get the best route for kiosks...\n{}",
          routePlanCheckUri.toUriString());

      Boolean isFinished = false;
      RouteOptimizationResponse routeOptimizationResponseBody = null;
      while (Boolean.FALSE.equals(isFinished)) {
        ResponseEntity<RouteOptimizationResponse> routeOptimizationResponseResponseEntity =
            restTemplate.postForEntity(
                routePlanCheckUri.toUriString(), mainRequest, RouteOptimizationResponse.class);

        routeOptimizationResponseBody = routeOptimizationResponseResponseEntity.getBody();
        isFinished = Objects.requireNonNull(routeOptimizationResponseBody).isFinished;

        try {
          log.info("Waiting for results for some 5 seconds...");
          TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException ie) {
          Thread.currentThread().interrupt();
        }
      }

      log.info(String.valueOf(routeOptimizationResponseBody));

      final List<Route> routes = routeOptimizationResponseBody.routes;

      // map routes to users
      Map<Integer, Long> routeToUser = new HashMap<>();
      routes.forEach(
          route -> routeToUser.put(route.routeNumber, users.get(route.routeNumber - 1).getId()));

      final Long[] optimizationIndex = {1000L};
      routes.forEach(
          route -> {
            final List<Stop> stops = route.stops;
            for (int i = 0; i < stops.size(); i++) {
              Stop stop = stops.get(i);
              if (stop.stopAddressId != 0) {
                Schedule schedule = new Schedule();
                final Long scheduleId = Long.valueOf(stop.stopAddressId);
                schedule.setId(scheduleId);
                final Schedule e = schedules.get(schedules.indexOf(schedule));
                final Long userId = routeToUser.get(route.routeNumber);
                scheduleService.setUser(scheduleId, userId);
                try {
                  e.setUser(
                      modelMapper.map(userService.findById(Long.valueOf(userId)), User.class));
                } catch (ValidationException | NotFoundException ex) {
                  ex.printStackTrace();
                }
                optimizedSchedules.add(e);
                int urgency = 1;
                if (e.getTask().getPriority().equals(TaskPriority.URGENT)) {
                  urgency = 100;
                }
                scheduleService.setOptimizationIndex(scheduleId, optimizationIndex[0] * urgency);
                optimizationIndex[0]--;
                //                                if (i == stops.size() - 1) {
                //
                // userService.setLastScheduledAddress(e.getTerminal().getLatitude(),
                //                                            e.getTerminal().getLongitude());
                //                                }
              }
            }
          });
    }
    return Optional.of(optimizedSchedules);
  }
}
