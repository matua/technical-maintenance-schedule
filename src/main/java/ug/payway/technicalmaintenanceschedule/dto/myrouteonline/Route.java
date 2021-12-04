
package ug.payway.technicalmaintenanceschedule.dto.myrouteonline;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import javax.validation.Valid;
import java.util.List;

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
}
