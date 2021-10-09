package com.matuageorge.technicalmaintenanceschedule.dto;

import com.matuageorge.technicalmaintenanceschedule.model.TerminalType;
import lombok.Data;

@Data
public class TerminalDto {
    private TerminalType type;
    private String name;
    private Boolean active;
    private Double longitude;
    private Double latitude;
}
