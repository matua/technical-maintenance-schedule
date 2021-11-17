package ug.payway.technicalmaintenanceschedule.service.api.routing;

import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;
import ug.payway.technicalmaintenanceschedule.exception.NotFoundException;
import ug.payway.technicalmaintenanceschedule.exception.ValidationException;
import ug.payway.technicalmaintenanceschedule.model.Schedule;
import ug.payway.technicalmaintenanceschedule.model.Terminal;
import ug.payway.technicalmaintenanceschedule.model.User;

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