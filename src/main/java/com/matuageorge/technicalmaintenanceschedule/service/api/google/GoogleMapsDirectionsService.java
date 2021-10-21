package com.matuageorge.technicalmaintenanceschedule.service.api.google;

import com.google.maps.errors.ApiException;
import com.matuageorge.technicalmaintenanceschedule.model.Terminal;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface GoogleMapsDirectionsService {
    Optional<List<Terminal>> getOptimalRouteListOfTerminals(List<Terminal> origins,
                                                            List<Terminal> destinations,
                                                            List<Terminal> terminalLocations) throws IOException, InterruptedException,
            ApiException;

    Optional<int[]> getOptimalOrderOfTerminals(List<Terminal> origins,
                                               List<Terminal> destinations,
                                               List<Terminal> terminalLocations);
}
