package com.matuageorge.technicalmaintenanceschedule.dto;

import lombok.Data;

@Data
public class UserAuthRequestDto {
    private String email;
    private String password;
}
