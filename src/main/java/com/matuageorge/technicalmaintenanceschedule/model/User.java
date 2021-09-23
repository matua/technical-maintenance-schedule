package com.matuageorge.technicalmaintenanceschedule.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
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
    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@(?:(?:[a-zA-Z0-9-]+\\.)?[a-zA-Z]+\\.)?matuageorge.com",
            message = "Must be only a company email")
    private String email;
    @Size(min = 6, max = 20, message
            = "Password cannot be blank and must be at least 6 characters")
    private String encryptedPassword;
    private Boolean active;
    private Boolean onDuty;
    @NotBlank
    @PositiveOrZero
    @Max(message = "Field hours cannot be more than 24 hours", value = 24)
    private Integer fieldHours;
}
