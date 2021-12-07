
package ug.payway.technicalmaintenanceschedule.model.graphhopper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Generated;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "vehicle_id",
        "type_id",
        "start_address",
        "earliest_start",
        "latest_end",
        "max_jobs",
        "skills"
})
@Generated("jsonschema2pojo")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vehicle {

    @JsonProperty("vehicle_id")
    public String vehicleId;
    @JsonProperty("type_id")
    public String typeId;
    @JsonProperty("start_address")
    public Address startAddress;
    @JsonProperty("earliest_start")
    public Integer earliestStart;
    @JsonProperty("latest_end")
    public Integer latestEnd;
    @JsonProperty("max_jobs")
    public Integer maxJobs;
    @JsonProperty("skills")
    public List<String> skills = null;

}
