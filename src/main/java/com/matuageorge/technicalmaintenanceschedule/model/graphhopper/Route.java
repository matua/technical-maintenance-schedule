package com.matuageorge.technicalmaintenanceschedule.model.graphhopper;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "vehicle_id",
        "shift_id",
        "distance",
        "transport_time",
        "completion_time",
        "waiting_time",
        "service_duration",
        "preparation_time",
        "activities"
})
@Generated("jsonschema2pojo")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Route {

    @JsonProperty("vehicle_id")
    public String vehicleId;
    @JsonProperty("shift_id")
    public String shiftId;
    @JsonProperty("distance")
    public Integer distance;
    @JsonProperty("transport_time")
    public Integer transportTime;
    @JsonProperty("completion_time")
    public Integer completionTime;
    @JsonProperty("waiting_time")
    public Integer waitingTime;
    @JsonProperty("service_duration")
    public Integer serviceDuration;
    @JsonProperty("preparation_time")
    public Integer preparationTime;
    @JsonProperty("activities")
    public List<Activity> activities = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Route withVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
        return this;
    }

    public Route withShiftId(String shiftId) {
        this.shiftId = shiftId;
        return this;
    }

    public Route withDistance(Integer distance) {
        this.distance = distance;
        return this;
    }

    public Route withTransportTime(Integer transportTime) {
        this.transportTime = transportTime;
        return this;
    }

    public Route withCompletionTime(Integer completionTime) {
        this.completionTime = completionTime;
        return this;
    }

    public Route withWaitingTime(Integer waitingTime) {
        this.waitingTime = waitingTime;
        return this;
    }

    public Route withServiceDuration(Integer serviceDuration) {
        this.serviceDuration = serviceDuration;
        return this;
    }

    public Route withPreparationTime(Integer preparationTime) {
        this.preparationTime = preparationTime;
        return this;
    }

    public Route withActivities(List<Activity> activities) {
        this.activities = activities;
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

    public Route withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}