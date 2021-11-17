package ug.payway.technicalmaintenanceschedule.dto.graphhopper;

import lombok.Builder;
import lombok.Data;
import ug.payway.technicalmaintenanceschedule.model.graphhopper.Objective;
import ug.payway.technicalmaintenanceschedule.model.graphhopper.Service;
import ug.payway.technicalmaintenanceschedule.model.graphhopper.Vehicle;

import java.util.List;

@Data
@Builder
public class RouteOptimizationRequest {
    private final List<Objective> objectives;
    private final List<Vehicle> vehicles;
    private final List<Service> services;
}
