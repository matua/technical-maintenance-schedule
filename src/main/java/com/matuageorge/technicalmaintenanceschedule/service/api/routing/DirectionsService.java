package com.matuageorge.technicalmaintenanceschedule.service.api.routing;

import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;
import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.Schedule;
import com.matuageorge.technicalmaintenanceschedule.model.Terminal;
import com.matuageorge.technicalmaintenanceschedule.model.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface DirectionsService {
    Optional<List<Terminal>> getOptimalRouteListOfTerminals(List<Terminal> origins,
                                                            List<Terminal> destinations,
                                                            List<Terminal> terminalLocations)
            throws IOException, InterruptedException, ApiException;

    Optional<int[]> getOptimalIndicesOfOrderOfTerminals(List<Terminal> origins,
                                                        List<Terminal> destinations,
                                                        List<Terminal> terminalLocations)
            throws IOException, InterruptedException;

    Optional<List<Terminal>> getOptimalRouteListOfTerminalsWithLatLngStartAndFinishPoint(LatLng origin,
                                                                                         List<Terminal> terminalLocations) throws IOException, InterruptedException, ApiException;

    default Optional<List<Schedule>> getOptimalIndicesOfOrderOfSchedules(List<Schedule> schedules, List<User> users) throws ValidationException, NotFoundException {
        return Optional.empty();
    }
}