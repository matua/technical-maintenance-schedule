package ug.payway.technicalmaintenanceschedule.dto.myrouteonline;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "jobToken",
        "isSuccessful"
})
@Generated("jsonschema2pojo")
public class JobResponse {

    @JsonProperty("jobToken")
    public String jobToken;
    @JsonProperty("isSuccessful")
    public Boolean isSuccessful;
}