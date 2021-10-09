package com.matuageorge.technicalmaintenanceschedule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@SuperBuilder
public class UserAuthResponseDto {

    private String token;
}