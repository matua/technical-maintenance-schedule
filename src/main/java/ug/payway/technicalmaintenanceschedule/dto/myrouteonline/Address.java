package ug.payway.technicalmaintenanceschedule.dto.myrouteonline;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "idNumber",
        "title",
        "serviceTimeInMinutes",
        "address"
})
@Generated("jsonschema2pojo")
@Builder
public class Address {

    @JsonProperty("idNumber")
    public Integer idNumber;
    @JsonProperty("title")
    public String title;
    @JsonProperty("serviceTimeInMinutes")
    public Integer serviceTimeInMinutes;
    @JsonProperty("address")
    public String address;
}
