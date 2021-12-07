
package ug.payway.technicalmaintenanceschedule.model.graphhopper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "address"
})
@Generated("jsonschema2pojo")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Delivery {

    @JsonProperty("address")
    public Address address;

}
