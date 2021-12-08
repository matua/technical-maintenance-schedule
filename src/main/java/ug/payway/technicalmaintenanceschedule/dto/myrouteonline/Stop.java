package ug.payway.technicalmaintenanceschedule.dto.myrouteonline;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "stopNumber",
  "stopAddressId",
  "address",
  "fullAddress",
  "title",
  "latitude",
  "longitude",
  "stopType",
  "travelTimeMinutes",
  "travelTimeTotalMinutes",
  "travelDistanceInMeters",
  "travelDistanceTotalInMeters"
})
@Generated("jsonschema2pojo")
public class Stop {

  @JsonProperty("stopNumber")
  public Integer stopNumber;

  @JsonProperty("stopAddressId")
  public Integer stopAddressId;

  @JsonProperty("address")
  public String address;

  @JsonProperty("fullAddress")
  public String fullAddress;

  @JsonProperty("title")
  public String title;

  @JsonProperty("latitude")
  public Double latitude;

  @JsonProperty("longitude")
  public Double longitude;

  @JsonProperty("stopType")
  public String stopType;

  @JsonProperty("travelTimeMinutes")
  public Integer travelTimeMinutes;

  @JsonProperty("travelTimeTotalMinutes")
  public Integer travelTimeTotalMinutes;

  @JsonProperty("travelDistanceInMeters")
  public Integer travelDistanceInMeters;

  @JsonProperty("travelDistanceTotalInMeters")
  public Integer travelDistanceTotalInMeters;
}
