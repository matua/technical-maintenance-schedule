
package ug.payway.technicalmaintenanceschedule.dto.myrouteonline;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import javax.validation.Valid;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "processingStatus",
        "processingProgress",
        "isFinished",
        "routes",
        "referenceId",
        "isSuccessful"
})
@Generated("jsonschema2pojo")
public class RouteOptimizationResponse {

    @JsonProperty("processingStatus")
    public String processingStatus;
    @JsonProperty("processingProgress")
    public Integer processingProgress;
    @JsonProperty("isFinished")
    public Boolean isFinished;
    @JsonProperty("routes")
    @Valid
    public List<Route> routes = null;
    @JsonProperty("referenceId")
    public String referenceId;
    @JsonProperty("isSuccessful")
    public Boolean isSuccessful;
}
