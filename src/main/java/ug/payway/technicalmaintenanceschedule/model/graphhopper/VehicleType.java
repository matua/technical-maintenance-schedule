
package ug.payway.technicalmaintenanceschedule.model.graphhopper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "type_id",
        "capacity",
        "profile"
})
@Generated("jsonschema2pojo")
public class VehicleType {

    @JsonProperty("type_id")
    public String typeId;
    @JsonProperty("capacity")
    public List<Integer> capacity = null;
    @JsonProperty("profile")
    public String profile;

}
