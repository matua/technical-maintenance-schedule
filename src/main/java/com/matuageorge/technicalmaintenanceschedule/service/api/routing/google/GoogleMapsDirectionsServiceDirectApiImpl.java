//package com.matuageorge.technicalmaintenanceschedule.service.api.routing.google;
//
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriComponents;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.util.List;
//import java.util.Map;
//
//@Service
//@Slf4j
//@AllArgsConstructor
//public class GoogleMapsDirectionsServiceDirectApiImpl implements GoogleMapsDirectionsService {
//
//    RestTemplate restTemplate;
//
//    @Override
//    public List<Map<Double, Double>> getOptimalRoute(List<Map<Double, Double>> origins,
//                                                     List<Map<Double, Double>> destinations,
//                                                     List<Map<Double, Double>> terminalLocations) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Accept", "application/json");
//
//        UriComponents uri = UriComponentsBuilder.newInstance()
//                .scheme("https")
//                .host("maps.googleapis.com")
//                .pathSegment("maps", "api", "directions", "json")
//                .queryParam("key", "AIzaSyBqySQ7-r5e7RBH5f9G0vXTDFWbvW-6XgY")
//                .queryParam("origin", convertTerminalLocationsToGoogleWaypointsParams(origins))
//                .queryParam("destination", convertTerminalLocationsToGoogleWaypointsParams(destinations))
//                .queryParam("waypoints",
//                        "optimize:true|" + convertTerminalLocationsToGoogleWaypointsParams(terminalLocations))
//                .build();
//
//        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(headers);
//

//        log.info("Trying to get the best route for kiosks via Google API...\n{}", uri.toUriString());
//
//        ResponseEntity<List<Map<Double, Double>>> responseEntity = restTemplate
//                .exchange(uri.toUriString(), HttpMethod.GET, requestEntity, new ParameterizedTypeReference<>() {
//                });
//
//        final List<Map<Double, Double>> body = responseEntity.getBody();
//        return body;
//    }
//
//    private String convertTerminalLocationsToGoogleWaypointsParams(
//            List<Map<Double, Double>> locations) {
//
//        StringBuilder waypointsParams = new StringBuilder();
//
//        locations.stream()
//                .flatMap(
//                        mapOfLongitudeAndLatitude -> mapOfLongitudeAndLatitude.entrySet().stream())
//                .forEach(
//                        entrySetOfLongitudeAndLatitude -> {
//                            waypointsParams
//                                    .append(entrySetOfLongitudeAndLatitude.getKey())
//                                    .append(",")
//                                    .append(entrySetOfLongitudeAndLatitude.getValue());
//                            if (locations.size() > 1) {
//                                waypointsParams.append("|");
//                            }
//                        }
//                );
//        return String.valueOf(waypointsParams);
//    }
//}
