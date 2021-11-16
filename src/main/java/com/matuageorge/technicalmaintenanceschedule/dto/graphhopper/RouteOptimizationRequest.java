package com.matuageorge.technicalmaintenanceschedule.dto.graphhopper;

import com.matuageorge.technicalmaintenanceschedule.model.graphhopper.Objective;
import com.matuageorge.technicalmaintenanceschedule.model.graphhopper.Service;
import com.matuageorge.technicalmaintenanceschedule.model.graphhopper.Vehicle;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RouteOptimizationRequest {
    private final List<Objective> objectives;
    private final List<Vehicle> vehicles;
    private final List<Service> services;
}
