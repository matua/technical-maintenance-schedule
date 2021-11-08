package com.matuageorge.technicalmaintenanceschedule.dto.grasshopper;

import com.matuageorge.technicalmaintenanceschedule.model.grasshopper.Objective;
import com.matuageorge.technicalmaintenanceschedule.model.grasshopper.Service;
import com.matuageorge.technicalmaintenanceschedule.model.grasshopper.Vehicle;
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
