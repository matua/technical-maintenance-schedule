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
  "type",
  "location_id",
  "address",
  "end_time",
  "end_date_time",
  "distance",
  "driving_time",
  "preparation_time",
  "waiting_time",
  "load_after",
  "id",
  "arr_time",
  "arr_date_time",
  "load_before"
})
@Generated("jsonschema2pojo")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Activity {
  @JsonProperty("type")
  private String type;

  @JsonProperty("location_id")
  private String locationId;

  @JsonProperty("address")
  private Address address;

  @JsonProperty("end_time")
  private Integer endTime;

  @JsonProperty("end_date_time")
  private Object endDateTime;

  @JsonProperty("distance")
  private Integer distance;

  @JsonProperty("driving_time")
  private Integer drivingTime;

  @JsonProperty("preparation_time")
  private Integer preparationTime;

  @JsonProperty("waiting_time")
  private Integer waitingTime;

  @JsonProperty("load_after")
  private List<Integer> loadAfter = null;

  @JsonProperty("id")
  private String id;

  @JsonProperty("arr_time")
  private Integer arrTime;

  @JsonProperty("arr_date_time")
  private Object arrDateTime;

  @JsonProperty("load_before")
  private List<Integer> loadBefore = null;
}
