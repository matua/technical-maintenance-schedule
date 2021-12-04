package ug.payway.technicalmaintenanceschedule.dto.myrouteonline;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

import javax.annotation.Generated;
import javax.validation.Valid;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "startAt",
        "endAt"
})
@Generated("jsonschema2pojo")
@Builder
public class SpecificRouteConstraint {

    @JsonProperty("startAt")
    @Valid
    public Address startAt;
    @JsonProperty("endAt")
    @Valid
    public Address endAt;
}
