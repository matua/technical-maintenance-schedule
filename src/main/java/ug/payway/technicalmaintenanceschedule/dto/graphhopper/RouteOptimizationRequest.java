
package com.matuageorge.dto.graphhopper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.maps.model.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ug.payway.technicalmaintenanceschedule.model.graphhopper.*;

import javax.annotation.Generated;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "vehicles",
        "vehicle_types",
        "services",
        "shipments",
        "objectives",
        "configuration"
})
@Generated("jsonschema2pojo")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RouteOptimizationRequest {

    @JsonProperty("vehicles")
    public List<Vehicle> vehicles = null;
    @JsonProperty("vehicle_types")
    public List<VehicleType> vehicleTypes = null;
    @JsonProperty("services")
    public List<Service> services = null;
    @JsonProperty("shipments")
    public List<Shipment> shipments = null;
    @JsonProperty("objectives")
    public List<Objective> objectives = null;
    @JsonProperty("configuration")
    public Configuration configuration;

}
