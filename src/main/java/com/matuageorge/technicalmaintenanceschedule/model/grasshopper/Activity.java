package com.matuageorge.technicalmaintenanceschedule.model.grasshopper;

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
        "type",
        "location_id",
        "address",
        "end_time",
        "end_date_time",
        "distance",
        "driving_time",
        "preparation_time",
        "waiting_time",
        "load_after",
        "id",
        "arr_time",
        "arr_date_time",
        "load_before"
})
@Generated("jsonschema2pojo")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Activity {

    @JsonProperty("type")
    public String type;
    @JsonProperty("location_id")
    public String locationId;
    @JsonProperty("address")
    public Address address;
    @JsonProperty("end_time")
    public Integer endTime;
    @JsonProperty("end_date_time")
    public Object endDateTime;
    @JsonProperty("distance")
    public Integer distance;
    @JsonProperty("driving_time")
    public Integer drivingTime;
    @JsonProperty("preparation_time")
    public Integer preparationTime;
    @JsonProperty("waiting_time")
    public Integer waitingTime;
    @JsonProperty("load_after")
    public List<Integer> loadAfter = null;
    @JsonProperty("id")
    public String id;
    @JsonProperty("arr_time")
    public Integer arrTime;
    @JsonProperty("arr_date_time")
    public Object arrDateTime;
    @JsonProperty("load_before")
    public List<Integer> loadBefore = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Activity withType(String type) {
        this.type = type;
        return this;
    }

    public Activity withLocationId(String locationId) {
        this.locationId = locationId;
        return this;
    }

    public Activity withAddress(Address address) {
        this.address = address;
        return this;
    }

    public Activity withEndTime(Integer endTime) {
        this.endTime = endTime;
        return this;
    }

    public Activity withEndDateTime(Object endDateTime) {
        this.endDateTime = endDateTime;
        return this;
    }

    public Activity withDistance(Integer distance) {
        this.distance = distance;
        return this;
    }

    public Activity withDrivingTime(Integer drivingTime) {
        this.drivingTime = drivingTime;
        return this;
    }

    public Activity withPreparationTime(Integer preparationTime) {
        this.preparationTime = preparationTime;
        return this;
    }

    public Activity withWaitingTime(Integer waitingTime) {
        this.waitingTime = waitingTime;
        return this;
    }

    public Activity withLoadAfter(List<Integer> loadAfter) {
        this.loadAfter = loadAfter;
        return this;
    }

    public Activity withId(String id) {
        this.id = id;
        return this;
    }

    public Activity withArrTime(Integer arrTime) {
        this.arrTime = arrTime;
        return this;
    }

    public Activity withArrDateTime(Object arrDateTime) {
        this.arrDateTime = arrDateTime;
        return this;
    }

    public Activity withLoadBefore(List<Integer> loadBefore) {
        this.loadBefore = loadBefore;
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

    public Activity withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}