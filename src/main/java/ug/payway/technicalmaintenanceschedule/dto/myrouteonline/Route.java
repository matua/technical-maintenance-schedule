
package ug.payway.technicalmaintenanceschedule.dto.myrouteonline;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "routeNumber",
        "totalStops",
        "stops"
})
@Generated("jsonschema2pojo")
public class Route {

    @JsonProperty("routeNumber")
    public Integer routeNumber;
    @JsonProperty("totalStops")
    public Integer totalStops;
    @JsonProperty("stops")
    @Valid
    public List<Stop> stops = null;
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
