package ug.payway.technicalmaintenanceschedule.model.graphhopper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Relation {
  private String type;
  private List<String> groups;
}
