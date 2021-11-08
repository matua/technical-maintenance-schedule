package com.matuageorge.technicalmaintenanceschedule.service.api.routing.grasshopper;

import com.google.maps.model.LatLng;
import com.matuageorge.technicalmaintenanceschedule.dto.grasshopper.RouteOptimizationRequest;
import com.matuageorge.technicalmaintenanceschedule.dto.grasshopper.RouteOptimizationResponse;
import com.matuageorge.technicalmaintenanceschedule.model.Terminal;
import com.matuageorge.technicalmaintenanceschedule.model.grasshopper.Address;
import com.matuageorge.technicalmaintenanceschedule.model.grasshopper.Objective;
import com.matuageorge.technicalmaintenanceschedule.model.grasshopper.Service;
import com.matuageorge.technicalmaintenanceschedule.model.grasshopper.Vehicle;
import com.matuageorge.technicalmaintenanceschedule.service.api.routing.DirectionsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@org.springframework.stereotype.Service("grasshopper")
@Slf4j
@RequiredArgsConstructor
@Primary
public class GrasshopperDirectionsServiceDirectApiImpl implements DirectionsService {

    private final RestTemplate restTemplate;
    @Value("${grasshopper.api.key}")
    private String grassHopperApiKey;

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

        log.info("Trying to get the best route for kiosks via Grasshopper API...\n{}", uri.toUriString());

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
                                                                                                List<Terminal> terminalLocations)
            throws IOException, InterruptedException {

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
}
