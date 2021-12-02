package ug.payway.technicalmaintenanceschedule.dto.myrouteonline;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "routingParameters",
        "routesConstraints",
        "addresses"
})
@Generated("jsonschema2pojo")
public class RouteOptimizationRequest {

    @JsonProperty("routingParameters")
    @Valid
    public RoutingParameters routingParameters;
    @JsonProperty("routesConstraints")
    @Valid
    public RoutesConstraints routesConstraints;
    @JsonProperty("addresses")
    @Valid
    public List<Address> addresses = null;
    @JsonIgnore
    @Valid
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
