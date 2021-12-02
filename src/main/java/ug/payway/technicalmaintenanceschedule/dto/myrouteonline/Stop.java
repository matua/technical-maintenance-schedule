
package ug.payway.technicalmaintenanceschedule.dto.myrouteonline;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

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
    @JsonIgnore
    @Valid
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
