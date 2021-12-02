
package ug.payway.technicalmaintenanceschedule.dto.myrouteonline;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @JsonIgnore
    @Valid
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
