package ug.payway.technicalmaintenanceschedule.service.api.routing.google;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.LatLng;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ug.payway.technicalmaintenanceschedule.model.Terminal;
import ug.payway.technicalmaintenanceschedule.service.api.routing.DirectionsService;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class GoogleMapsDirectionsServiceJavaClientApiImpl implements DirectionsService {
  private static final String GOOGLE_NO_WAYPOINTS_MESSAGE =
      "No waypoints or routes returned due to Google API " + "error: {}";
  private final GeoApiContext geoApiContext;

  @Override
  public Optional<List<Terminal>> getOptimalRouteListOfTerminals(
      List<Terminal> origins, List<Terminal> destinations, List<Terminal> terminalLocations)
      throws IOException, InterruptedException {

    log.info(
        "Getting optimal route via Google Java Client API and returning list of terminals in an optimized "
            + "order");

    try {
      DirectionsResult result = getDirectionsResult(origins, destinations, terminalLocations);
      final List<Terminal> terminals =
          convertArrayOfGeocodeWayPointsToListOfTerminals(terminalLocations, result.routes);
      geoApiContext.shutdown();
      return Optional.of(terminals);
    } catch (ApiException apiException) {
      log.info(GOOGLE_NO_WAYPOINTS_MESSAGE, apiException.getLocalizedMessage());
    }
    return Optional.empty();
  }

  @Override
  public Optional<List<Terminal>> getOptimalRouteListOfTerminalsWithLatLngStartAndFinishPoint(
      LatLng startAndFinishPoint, List<Terminal> terminalLocations)
      throws IOException, InterruptedException {

    log.info(
        "Getting optimal route via Google Java Client API and returning list of terminals in an optimized "
            + "order");
    try {
      DirectionsResult result =
          getDirectionsResultWithLatLngStartAndFinishPoint(startAndFinishPoint, terminalLocations);
      final List<Terminal> terminals =
          convertArrayOfGeocodeWayPointsToListOfTerminals(terminalLocations, result.routes);
      geoApiContext.shutdown();
      return Optional.of(terminals);
    } catch (ApiException apiException) {
      log.info(
          "No waypoints or routes returned due to Google API error: {}",
          apiException.getLocalizedMessage());
    }
    return Optional.empty();
  }

  private DirectionsResult getDirectionsResultWithLatLngStartAndFinishPoint(
      LatLng startAndFinishPoint, List<Terminal> terminalLocations)
      throws IOException, InterruptedException, ApiException {
    return DirectionsApi.newRequest(geoApiContext)
        .origin(startAndFinishPoint)
        .destination(startAndFinishPoint)
        .departureTime(Instant.now())
        .waypoints(convertListOfTerminalToArrayOfLatLngWaypoints(terminalLocations))
        .optimizeWaypoints(true)
        .await();
  }

  @Override
  public Optional<int[]> getOptimalIndicesOfOrderOfTerminals(
      List<Terminal> origins, List<Terminal> destinations, List<Terminal> terminalLocations)
      throws IOException, InterruptedException {

    log.info(
        "Getting optimal route via Google Java Client API and returning an optimized order as an Integer "
            + "array ");

    try {
      DirectionsResult result = getDirectionsResult(origins, destinations, terminalLocations);
      final int[] waypointOrder = result.routes[0].waypointOrder;
      geoApiContext.shutdown();
      return Optional.of(waypointOrder);
    } catch (ApiException apiException) {
      log.info(GOOGLE_NO_WAYPOINTS_MESSAGE, apiException.getLocalizedMessage());
    }
    return Optional.empty();
  }

  private DirectionsResult getDirectionsResult(
      List<Terminal> origins, List<Terminal> destinations, List<Terminal> terminalLocations)
      throws ApiException, InterruptedException, IOException {
    return DirectionsApi.newRequest(geoApiContext)
        .origin(origins.get(0).getLatLngLocation())
        .destination(destinations.get(0).getLatLngLocation())
        .departureTime(Instant.now())
        .waypoints(convertListOfTerminalToArrayOfLatLngWaypoints(terminalLocations))
        .optimizeWaypoints(true)
        .await();
  }

  private List<Terminal> convertArrayOfGeocodeWayPointsToListOfTerminals(
      List<Terminal> terminalLocations, DirectionsRoute... directionsRoutes) {

    log.info("Converting array of Geocode waypoints to list of terminals..");

    List<Terminal> optimizedTerminalRoute = new ArrayList<>();
    final int[] waypointsOrder = directionsRoutes[0].waypointOrder;

    log.info("The updated order of terminals: {}", waypointsOrder);
    for (int index : waypointsOrder) {
      optimizedTerminalRoute.add(terminalLocations.get(index));
    }
    return optimizedTerminalRoute;
  }

  private LatLng[] convertListOfTerminalToArrayOfLatLngWaypoints(List<Terminal> terminalLocations) {

    log.info("Converting list of terminals to array of LatLng waypoints..");

    return terminalLocations.stream()
        .map(Terminal::getLatLngLocation)
        .toList()
        .toArray(new LatLng[0]);
  }
}
