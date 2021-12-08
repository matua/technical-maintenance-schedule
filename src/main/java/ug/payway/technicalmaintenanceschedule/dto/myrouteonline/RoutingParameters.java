package ug.payway.technicalmaintenanceschedule.dto.myrouteonline;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"startTime", "visitClosestFirst"})
@Generated("jsonschema2pojo")
@Builder
public class RoutingParameters {

  @JsonProperty("startTime")
  public String startTime;

  @JsonProperty("visitClosestFirst")
  public Boolean visitClosestFirst;
}
