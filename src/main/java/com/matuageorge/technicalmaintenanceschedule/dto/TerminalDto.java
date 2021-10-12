package com.matuageorge.technicalmaintenanceschedule.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.matuageorge.technicalmaintenanceschedule.model.TerminalType;
import lombok.Data;

@Data
public class TerminalDto {
    @JsonIgnore
    private Boolean deleted;
    @JsonProperty("disabled")
    private Boolean active;
    @JsonProperty("id")
    private Long terminalId;
    private String location;
    private String name;
    @JsonIgnore
    private Integer terminalGroupId;
    private TerminalType type;
}