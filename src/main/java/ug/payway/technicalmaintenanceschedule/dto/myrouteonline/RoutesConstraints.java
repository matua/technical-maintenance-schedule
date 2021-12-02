package ug.payway.technicalmaintenanceschedule.dto.myrouteonline;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "isFixedNumberOfRoutes",
        "fixedNumberOfRoutes",
        "specificRouteConstraints"
})
@Generated("jsonschema2pojo")
public class RoutesConstraints {

    @JsonProperty("isFixedNumberOfRoutes")
    public Boolean isFixedNumberOfRoutes;
    @JsonProperty("fixedNumberOfRoutes")
    public Integer fixedNumberOfRoutes;
    @JsonProperty("specificRouteConstraints")
    @Valid
    public List<SpecificRouteConstraint> specificRouteConstraints = null;
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
