package com.matuageorge.technicalmaintenanceschedule.dto;

import com.matuageorge.technicalmaintenanceschedule.model.TerminalType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
public class TerminalDto extends AbstractBaseDto {
    private TerminalType type;
    private String name;
    private Boolean active;
    private Double longitude;
    private Double latitude;
}
