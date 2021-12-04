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
        "isFixedNumberOfRoutes",
        "fixedNumberOfRoutes",
        "specificRouteConstraints"
})
@Generated("jsonschema2pojo")
@Builder
public class RoutesConstraints {

    @JsonProperty("isFixedNumberOfRoutes")
    public Boolean isFixedNumberOfRoutes;
    @JsonProperty("fixedNumberOfRoutes")
    public Integer fixedNumberOfRoutes;
    @JsonProperty("specificRouteConstraints")
    @Valid
    public List<SpecificRouteConstraint> specificRouteConstraints = null;
}
