package com.matuageorge.technicalmaintenanceschedule.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
@Table(name = "tasks")
@Entity
public class Task extends AbstractBaseEntity {
    @Size(min = 1, max = 300, message
            = "Description must be between 1 and 300 characters")
    private String description;
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;
    @Min(message = "Frequency must be minimum every 1 day", value = 1)
    @Max(message = "Frequency must be maximum every 365 days", value = 365)
    private Integer frequency;
}
