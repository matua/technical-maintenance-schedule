package ug.payway.technicalmaintenanceschedule.dto.graphhopper;

import com.fasterxml.jackson.annotation.*;
import ug.payway.technicalmaintenanceschedule.model.graphhopper.Solution;

import javax.annotation.processing.Generated;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "copyrights",
  "job_id",
  "status",
  "waiting_time_in_queue",
  "processing_time",
  "solution"
})
@Generated("jsonschema2pojo")
public class RouteOptimizationResponse {

  @JsonProperty("copyrights")
  public List<String> copyrights = null;

  @JsonProperty("job_id")
  public String jobId;

  @JsonProperty("status")
  public String status;

  @JsonProperty("waiting_time_in_queue")
  public Integer waitingTimeInQueue;

  @JsonProperty("processing_time")
  public Integer processingTime;

  @JsonProperty("solution")
  public Solution solution;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  public RouteOptimizationResponse withCopyrights(List<String> copyrights) {
    this.copyrights = copyrights;
    return this;
  }

  public RouteOptimizationResponse withJobId(String jobId) {
    this.jobId = jobId;
    return this;
  }

  public RouteOptimizationResponse withStatus(String status) {
    this.status = status;
    return this;
  }

  public RouteOptimizationResponse withWaitingTimeInQueue(Integer waitingTimeInQueue) {
    this.waitingTimeInQueue = waitingTimeInQueue;
    return this;
  }

  public RouteOptimizationResponse withProcessingTime(Integer processingTime) {
    this.processingTime = processingTime;
    return this;
  }

  public RouteOptimizationResponse withSolution(Solution solution) {
    this.solution = solution;
    return this;
  }

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }

  public RouteOptimizationResponse withAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
    return this;
  }
}
