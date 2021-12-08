package ug.payway.technicalmaintenanceschedule.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ug.payway.technicalmaintenanceschedule.model.TerminalType;

@Data
public class TerminalDto {
  private Boolean deleted;
  private Boolean disabled;

  @JsonProperty("id")
  private Long terminalId;

  private String location;
  private String name;
  private Integer terminalGroupId;
  private TerminalType type;
  private Double longitude;
  private Double latitude;
}
