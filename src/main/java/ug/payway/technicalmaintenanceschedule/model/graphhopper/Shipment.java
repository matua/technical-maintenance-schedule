
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
        "id",
        "name",
        "priority",
        "pickup",
        "delivery",
        "size",
        "required_skills"
})
@Generated("jsonschema2pojo")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Shipment {

    @JsonProperty("id")
    public String id;
    @JsonProperty("name")
    public String name;
    @JsonProperty("priority")
    public Integer priority;
    @JsonProperty("pickup")
    public Pickup pickup;
    @JsonProperty("delivery")
    public Delivery delivery;
    @JsonProperty("size")
    public List<Integer> size = null;
    @JsonProperty("required_skills")
    public List<String> requiredSkills = null;

}
