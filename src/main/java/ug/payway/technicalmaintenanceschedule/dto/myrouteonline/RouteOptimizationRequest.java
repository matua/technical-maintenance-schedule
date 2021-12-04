package ug.payway.technicalmaintenanceschedule.dto.myrouteonline;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

import javax.annotation.Generated;
import javax.validation.Valid;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "routingParameters",
        "routesConstraints",
        "addresses"
})
@Generated("jsonschema2pojo")
@Builder
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
}
