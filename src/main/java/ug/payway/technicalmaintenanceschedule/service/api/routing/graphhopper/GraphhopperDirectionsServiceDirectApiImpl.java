package ug.payway.technicalmaintenanceschedule.service.api.routing.graphhopper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.model.LatLng;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ug.payway.technicalmaintenanceschedule.dto.graphhopper.RouteOptimizationResponse;
import ug.payway.technicalmaintenanceschedule.model.*;
import ug.payway.technicalmaintenanceschedule.model.graphhopper.*;
import ug.payway.technicalmaintenanceschedule.service.ScheduleService;
import ug.payway.technicalmaintenanceschedule.service.UserLocationService;
import ug.payway.technicalmaintenanceschedule.service.api.routing.DirectionsService;

import java.util.*;

@org.springframework.stereotype.Service("graphhopper")
@Slf4j
@RequiredArgsConstructor
@Primary
public class GraphhopperDirectionsServiceDirectApiImpl implements DirectionsService {

  private final RestTemplate restTemplate;
  private final ScheduleService scheduleService;
  private final UserLocationService userLocationService;
  private final ObjectMapper objectMapper;

  @Value("${graphhopper.api.key}")
  private String graphHopperApiKey;

  @Override
  public Optional<List<Terminal>> getOptimalRouteListOfTerminals(
      List<Terminal> origins, List<Terminal> destinations, List<Terminal> terminalLocations) {

    log.info(
        "Getting optimal route via Graphhopper API and returning list of terminals in an optimized "
            + "order");

    List<Objective> objectives =
        List.of(Objective.builder().type("min-max").value("completion_time").build());
    List<Vehicle> vehicles =
        List.of(
            Vehicle.builder()
                .vehicleId("tech")
                .startAddress(
                    Address.builder()
                        .locationId("head_office")
                        .lon(origins.get(0).getLongitude())
                        .lat(origins.get(0).getLatitude())
                        .build())
                .build());

    List<Service> services = new ArrayList<>();
    terminalLocations.forEach(
        terminal ->
            services.add(
                Service.builder()
                    .id(terminal.getName())
                    .name(terminal.getLocation())
                    .address(
                        Address.builder()
                            .locationId(terminal.getName())
                            .lon(terminal.getLongitude())
                            .lat(terminal.getLatitude())
                            .build())
                    .build()));

    com.matuageorge.dto.graphhopper.RouteOptimizationRequest routeOptimizationRequest =
        com.matuageorge.dto.graphhopper.RouteOptimizationRequest.builder()
            .objectives(objectives)
            .vehicles(vehicles)
            .services(services)
            .build();

    UriComponents uri =
        UriComponentsBuilder.newInstance()
            .scheme("https")
            .host("graphhopper.com")
            .pathSegment("api", "1", "vrp")
            .queryParam("key", graphHopperApiKey)
            .build();

    final ResponseEntity<RouteOptimizationResponse> routeOptimizationResponseResponseEntity =
        restTemplate.postForEntity(
            uri.toUriString(), routeOptimizationRequest, RouteOptimizationResponse.class);

    log.info("Trying to get the best route for kiosks via Graphhopper API...");

    final RouteOptimizationResponse body = routeOptimizationResponseResponseEntity.getBody();

    List<Terminal> listOfOrderedTerminals = new ArrayList<>();
    if (body != null) {
      body.solution
          .routes
          .get(0)
          .activities
          .forEach(
              activity ->
                  listOfOrderedTerminals.add(Terminal.builder().name(activity.getId()).build()));
      return Optional.of(listOfOrderedTerminals);
    }
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
    log.info("Building a request object for Graphhopper API..");

    List<Objective> objectives =
        List.of(Objective.builder().type("min-max").value("completion_time").build());

    List<Vehicle> vehicles = convertListOfUsersToListOfVehicles(users);

    boolean urgentSchedulesExist = false;

    for (Schedule schedule : schedules) {
      if (schedule.getTask().getPriority() == TaskPriority.URGENT) {
        urgentSchedulesExist = true;
        break;
      }
    }

    List<String> groups = new ArrayList<>();
    Relation relation = null;
    if (urgentSchedulesExist) {
      groups.add(TaskPriority.URGENT.getName());
      groups.add(TaskPriority.COMMON.getName());
      relation = Relation.builder().groups(groups).type("in_direct_sequence").build();
    }

    List<Service> services = new ArrayList<>();

    schedules.forEach(
        schedule -> {
          final TaskPriority priority = schedule.getTask().getPriority();
          services.add(
              Service.builder()
                  .id(String.valueOf(schedule.getId()))
                  .name(schedule.getTerminal().getLocation())
                  .address(
                      Address.builder()
                          .locationId(schedule.getTerminal().getName())
                          .lon(schedule.getTerminal().getLongitude())
                          .lat(schedule.getTerminal().getLatitude())
                          .build())
                  .priority(priority)
                  .group(priority.getName())
                  .build());
        });

    com.matuageorge.dto.graphhopper.RouteOptimizationRequest routeOptimizationRequest =
        com.matuageorge.dto.graphhopper.RouteOptimizationRequest.builder()
            .objectives(objectives)
            .vehicles(vehicles)
            .services(services)
            .build();

    if (relation != null) {
      routeOptimizationRequest.setRelations(List.of(relation));
    }

    try {
      final String s = objectMapper.writeValueAsString(routeOptimizationRequest);
      log.info("JSON PAYLOAD REQUEST:\n____________________\n{}", s);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    UriComponents uri =
        UriComponentsBuilder.newInstance()
            .scheme("https")
            .host("graphhopper.com")
            .pathSegment("api", "1", "vrp")
            .queryParam("key", graphHopperApiKey)
            .build();

    log.info(
        "Shooting at Graphhopper API to get the best route for kiosks...\n{}", uri.toUriString());

    final ResponseEntity<RouteOptimizationResponse> routeOptimizationResponseResponseEntity =
        restTemplate.postForEntity(
            uri.toUriString(), routeOptimizationRequest, RouteOptimizationResponse.class);

    final RouteOptimizationResponse body = routeOptimizationResponseResponseEntity.getBody();

    final List<Route> routes = body != null ? body.solution.routes : List.of();

    HashMap<Integer, Schedule[]> partialOptimizedOrderSchedules = new HashMap<>();
    for (int i = 0; i < routes.size(); i++) {
      partialOptimizedOrderSchedules.put(i, new Schedule[routes.get(i).activities.size()]);
    }

    schedules.forEach(
        schedule -> {
          int i = 0;
          while (i < routes.size()) {
            Route route = routes.get(i);
            for (int j = 0; j < route.activities.size(); j++) {
              final String routeId = route.activities.get(j).getId();
              if (routeId != null) {
                final Long scheduleId = schedule.getId();
                if (String.valueOf(scheduleId).equals(routeId)) {
                  partialOptimizedOrderSchedules.get(i)[j] = schedule;
                  scheduleService.setUser(scheduleId, Long.valueOf(route.vehicleId));
                  scheduleService.setOptimizationIndex(scheduleId, (long) j);
                }
              }
            }
            i++;
          }
        });

    final List<Schedule> value =
        partialOptimizedOrderSchedules.values().stream().flatMap(Arrays::stream).toList();
    return Optional.of(value);
  }

  private List<Vehicle> convertListOfUsersToListOfVehicles(List<User> users) {
    List<Vehicle> vehicles = new ArrayList<>();
    users.forEach(
        user -> {
          final UserLocation userLocationServiceLastByUser =
              userLocationService.findLastByUser(user);
          vehicles.add(
              Vehicle.builder()
                  .vehicleId(String.valueOf(user.getId()))
                  .startAddress(
                      Address.builder()
                          .locationId("Start Location")
                          .lat(userLocationServiceLastByUser.getLatitude())
                          .lon(userLocationServiceLastByUser.getLongitude())
                          .build())
                  .build());
        });
    return vehicles;
  }
}
