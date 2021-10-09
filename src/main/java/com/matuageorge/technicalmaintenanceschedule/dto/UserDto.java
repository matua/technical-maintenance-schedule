package com.matuageorge.technicalmaintenanceschedule.dto;

import com.matuageorge.technicalmaintenanceschedule.model.Role;
import lombok.Data;

@Data
public class UserDto {
    private Role role;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean active;
    private Boolean onDuty;
    private Integer fieldHours;
}
