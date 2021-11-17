package ug.payway.technicalmaintenanceschedule;

/*
 * Copyright 2014 Google Inc. All rights reserved.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under
 * the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF
 * ANY KIND, either express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.LatLng;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DirectionsApiTest {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(DirectionsApiTest.class);
    GeoApiContext sc;
    @Value("${google.directions.api.key}")
    private String googleApiKey;

    @BeforeEach
    void before() {
        sc = new GeoApiContext.Builder()
                .apiKey(googleApiKey).build();
    }


    /**
     * Tests that calling {@code optimizeWaypoints(true)} works in either order.
     */
    @Test
    void testOptimizeWaypointsAfterWaypoints() throws Exception {
        List<LatLng> waypoints = getOptimizationWaypoints();
        LatLng origin = waypoints.get(0);
        LatLng destination = waypoints.get(1);
        DirectionsResult result =
                DirectionsApi.newRequest(sc)
                        .origin(origin)
                        .destination(destination)
                        .departureTime(Instant.now())
                        .waypoints(waypoints.subList(2, waypoints.size()).toArray(new LatLng[0]))
                        .optimizeWaypoints(true)
                        .await();

        assertNotNull(result.toString());
        log.info(Arrays.toString(result.routes[0].waypointOrder));
    }

    /**
     * Coordinates in Mexico City.
     */
    private List<LatLng> getOptimizationWaypoints() {
        List<LatLng> waypoints = new ArrayList<>();
        waypoints.add(new LatLng(32.60611550982706, 32.5640432));
        waypoints.add(new LatLng(32.5640432, 0.3106486));
        waypoints.add(new LatLng(19.435436, -99.139145));
        waypoints.add(new LatLng(19.396436, -99.157176));
        waypoints.add(new LatLng(19.427705, -99.198858));
        waypoints.add(new LatLng(19.425869, -99.160716));
        return waypoints;
    }
}