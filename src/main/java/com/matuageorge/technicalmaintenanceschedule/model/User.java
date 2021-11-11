package com.matuageorge.technicalmaintenanceschedule.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
@Table(name = "users")
@Entity
public class User extends AbstractBaseEntity {
    @Enumerated(EnumType.STRING)
    private Role role;
    @Size(min = 1, max = 20, message
            = "First name must be between 1 and 20 characters")
    private String firstName;
    @Size(min = 1, max = 20, message
            = "Last name must be between 1 and 20 characters")
    private String lastName;
    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@(?:(?:[a-zA-Z0-9-]+\\.)?[a-zA-Z]+\\.)?payway.ug",
            message = "Must be only a company email")
    private String email;
    @Max(value = 500, message = "Encrypted password cannot be more than 500 characters")
    private String encryptedPassword;
    private Boolean active;
    private Boolean onDuty;
    @NotBlank
    @PositiveOrZero
    @Max(message = "Field hours cannot be more than 24 hours", value = 24)
    private Integer fieldHours;
    private Double baseLongitude;
    private Double baseLatitude;
}
