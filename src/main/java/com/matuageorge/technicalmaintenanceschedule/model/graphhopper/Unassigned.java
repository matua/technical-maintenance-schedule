
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
        "services",
        "shipments",
        "breaks",
        "details"
})
@Generated("jsonschema2pojo")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Unassigned {

    @JsonProperty("services")
    public List<Object> services = null;
    @JsonProperty("shipments")
    public List<Object> shipments = null;
    @JsonProperty("breaks")
    public List<Object> breaks = null;
    @JsonProperty("details")
    public List<Object> details = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Unassigned withServices(List<Object> services) {
        this.services = services;
        return this;
    }

    public Unassigned withShipments(List<Object> shipments) {
        this.shipments = shipments;
        return this;
    }

    public Unassigned withBreaks(List<Object> breaks) {
        this.breaks = breaks;
        return this;
    }

    public Unassigned withDetails(List<Object> details) {
        this.details = details;
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

    public Unassigned withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }
}