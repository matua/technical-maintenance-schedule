package com.matuageorge.technicalmaintenanceschedule.dto;

import com.matuageorge.technicalmaintenanceschedule.model.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
public class UserDto extends AbstractBaseDto {
    private Role role;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean active;
    private Boolean onDuty;
    private Integer fieldHours;
}
