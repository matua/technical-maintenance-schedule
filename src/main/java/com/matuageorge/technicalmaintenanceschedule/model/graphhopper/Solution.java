
package com.matuageorge.technicalmaintenanceschedule.model.graphhopper;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.processing.Generated;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "costs",
        "distance",
        "time",
        "transport_time",
        "completion_time",
        "max_operation_time",
        "waiting_time",
        "service_duration",
        "preparation_time",
        "no_vehicles",
        "no_unassigned",
        "routes",
        "unassigned"
})
@Generated("jsonschema2pojo")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Solution {

    @JsonProperty("costs")
    public Integer costs;
    @JsonProperty("distance")
    public Integer distance;
    @JsonProperty("time")
    public Integer time;
    @JsonProperty("transport_time")
    public Integer transportTime;
    @JsonProperty("completion_time")
    public Integer completionTime;
    @JsonProperty("max_operation_time")
    public Integer maxOperationTime;
    @JsonProperty("waiting_time")
    public Integer waitingTime;
    @JsonProperty("service_duration")
    public Integer serviceDuration;
    @JsonProperty("preparation_time")
    public Integer preparationTime;
    @JsonProperty("no_vehicles")
    public Integer noVehicles;
    @JsonProperty("no_unassigned")
    public Integer noUnassigned;
    @JsonProperty("routes")
    public List<Route> routes = null;
    @JsonProperty("unassigned")
    public Unassigned unassigned;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Solution withCosts(Integer costs) {
        this.costs = costs;
        return this;
    }

    public Solution withDistance(Integer distance) {
        this.distance = distance;
        return this;
    }

    public Solution withTime(Integer time) {
        this.time = time;
        return this;
    }

    public Solution withTransportTime(Integer transportTime) {
        this.transportTime = transportTime;
        return this;
    }

    public Solution withCompletionTime(Integer completionTime) {
        this.completionTime = completionTime;
        return this;
    }

    public Solution withMaxOperationTime(Integer maxOperationTime) {
        this.maxOperationTime = maxOperationTime;
        return this;
    }

    public Solution withWaitingTime(Integer waitingTime) {
        this.waitingTime = waitingTime;
        return this;
    }

    public Solution withServiceDuration(Integer serviceDuration) {
        this.serviceDuration = serviceDuration;
        return this;
    }

    public Solution withPreparationTime(Integer preparationTime) {
        this.preparationTime = preparationTime;
        return this;
    }

    public Solution withNoVehicles(Integer noVehicles) {
        this.noVehicles = noVehicles;
        return this;
    }

    public Solution withNoUnassigned(Integer noUnassigned) {
        this.noUnassigned = noUnassigned;
        return this;
    }

    public Solution withRoutes(List<Route> routes) {
        this.routes = routes;
        return this;
    }

    public Solution withUnassigned(Unassigned unassigned) {
        this.unassigned = unassigned;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Solution withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}