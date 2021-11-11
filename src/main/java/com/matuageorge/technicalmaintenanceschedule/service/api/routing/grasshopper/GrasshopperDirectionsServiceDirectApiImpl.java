package com.matuageorge.technicalmaintenanceschedule.service.api.routing.grasshopper;

import com.google.maps.model.LatLng;
import com.matuageorge.technicalmaintenanceschedule.dto.grasshopper.RouteOptimizationRequest;
import com.matuageorge.technicalmaintenanceschedule.dto.grasshopper.RouteOptimizationResponse;
import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.Schedule;
import com.matuageorge.technicalmaintenanceschedule.model.Terminal;
import com.matuageorge.technicalmaintenanceschedule.model.User;
import com.matuageorge.technicalmaintenanceschedule.model.grasshopper.*;
import com.matuageorge.technicalmaintenanceschedule.service.ScheduleService;
import com.matuageorge.technicalmaintenanceschedule.service.UserService;
import com.matuageorge.technicalmaintenanceschedule.service.api.routing.DirectionsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;


@org.springframework.stereotype.Service("grasshopper")
@Slf4j
@RequiredArgsConstructor
@Primary
public class GrasshopperDirectionsServiceDirectApiImpl implements DirectionsService {

    private final RestTemplate restTemplate;
    @Value("${grasshopper.api.key}")
    private String grassHopperApiKey;
    private ScheduleService scheduleService;
    private UserService userService;

    @Override
    public Optional<List<Terminal>> getOptimalRouteListOfTerminals(List<Terminal> origins,
                                                                   List<Terminal> destinations,
                                                                   List<Terminal> terminalLocations) {

        log.info("Getting optimal route via Google Java Client API and returning list of terminals in an optimized " +
                "order");

        List<Objective> objectives = List.of(Objective.builder()
                .type("min-max")
                .value("completion_time")
                .build());
        List<Vehicle> vehicles = List.of(
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
        terminalLocations.forEach(terminal -> services.add(Service.builder()
                .id(terminal.getName())
                .name(terminal.getLocation())
                .address(Address.builder()
                        .locationId(terminal.getName())
                        .lon(terminal.getLongitude())
                        .lat(terminal.getLatitude())
                        .build())
                .build()));

        RouteOptimizationRequest routeOptimizationRequest = RouteOptimizationRequest.builder()
                .objectives(objectives)
                .vehicles(vehicles)
                .services(services)
                .build();

        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("graphhopper.com")
                .pathSegment("api", "1", "vrp")
                .queryParam("key", grassHopperApiKey)
                .build();

        final ResponseEntity<RouteOptimizationResponse> routeOptimizationResponseResponseEntity = restTemplate.postForEntity(uri.toUriString(), routeOptimizationRequest,
                RouteOptimizationResponse.class);

        log.info("Trying to get the best route for kiosks via Grasshopper API...");

        final RouteOptimizationResponse body = routeOptimizationResponseResponseEntity.getBody();

        List<Terminal> listOfOrderedTerminals = new ArrayList<>();
        if (body != null) {
            body.solution.routes.get(0).activities.forEach(
                    activity -> listOfOrderedTerminals.add(Terminal.builder()
                            .name(activity.id)
                            .build())
            );
            return Optional.of(listOfOrderedTerminals);
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<Terminal>> getOptimalRouteListOfTerminalsWithLatLngStartAndFinishPoint(LatLng startAndFinishPoint,
                                                                                                List<Terminal> terminalLocations) {

        log.info("Getting optimal route via Google Java Client API and returning list of terminals in an optimized " +
                "order");

        return Optional.empty();
    }


    @Override
    public Optional<int[]> getOptimalIndicesOfOrderOfTerminals(List<Terminal> origins, List<Terminal> destinations,
                                                               List<Terminal> terminalLocations) {

        log.info("Getting optimal route via Google Java Client API and returning an optimized order as an Integer " +
                "array ");

        return Optional.empty();
    }

    @Override
    public Optional<List<Schedule>> getOptimalIndicesOfOrderOfSchedules(List<Schedule> schedules, List<User> users) throws ValidationException, NotFoundException {
        log.info("Building a request object for Grasshopper API..");

        List<Objective> objectives = List.of(Objective.builder()
                .type("min-max")
                .value("completion_time")
                .build());


        List<Vehicle> vehicles = convertListOfUsersToListOfVehicles(users);

        List<Service> services = new ArrayList<>();
        schedules.forEach(
                schedule -> services.add(
                        Service.builder()
                                .id(String.valueOf(schedule.getId()))
                                .name(schedule.getTerminal().getLocation())
                                .address(Address.builder()
                                        .locationId(schedule.getTerminal().getName())
                                        .lon(schedule.getTerminal().getLongitude())
                                        .lat(schedule.getTerminal().getLatitude())
                                        .build())
                                .build())
        );

        RouteOptimizationRequest routeOptimizationRequest = RouteOptimizationRequest.builder()
                .objectives(objectives)
                .vehicles(vehicles)
                .services(services)
                .build();

        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("graphhopper.com")
                .pathSegment("api", "1", "vrp")
                .queryParam("key", grassHopperApiKey)
                .build();

        log.info("Shooting at Grasshopper API to get the best route for kiosks...\n{}",
                uri.toUriString());

        final ResponseEntity<RouteOptimizationResponse> routeOptimizationResponseResponseEntity = restTemplate.postForEntity(uri.toUriString(), routeOptimizationRequest,
                RouteOptimizationResponse.class);


        final RouteOptimizationResponse body = routeOptimizationResponseResponseEntity.getBody();

        final List<Route> routes = body != null ? body.solution.routes : List.of();

        HashMap<Integer, Schedule[]> partialOptimizedOrderSchedules = new HashMap<>();
        for (int i = 0; i < routes.size(); i++) {
            partialOptimizedOrderSchedules.put(i, new Schedule[routes.get(i).activities.size()]);
        }

        schedules.forEach(schedule -> {
            for (int i = 0; i < routes.size(); i++) {
                Route route = routes.get(i);
                for (int j = 0; j < route.activities.size(); j++) {
                    final String routeId = route.activities.get(j).id;
                    if (routeId != null) {
                        if (String.valueOf(schedule.getId()).equals(routeId)) {
                            partialOptimizedOrderSchedules.get(i)[j] = schedule;
                        }
                    }
                }
            }
        });

        return Optional.of(partialOptimizedOrderSchedules.values()
                .stream()
                .flatMap(Arrays::stream)
                .toList());
    }

    private List<Vehicle> convertListOfUsersToListOfVehicles(List<User> users) {
        List<Vehicle> vehicles = new ArrayList<>();
        users.forEach(user -> vehicles.add(Vehicle.builder()
                .vehicleId(String.valueOf(user.getId()))
                .startAddress(
                        Address.builder()
                                .locationId("Head Office")
                                .lon(user.getBaseLongitude())
                                .lat(user.getBaseLatitude())
                                .build())
                .build()));
        return vehicles;
    }
}
