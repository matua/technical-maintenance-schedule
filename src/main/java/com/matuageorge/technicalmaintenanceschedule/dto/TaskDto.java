package com.matuageorge.technicalmaintenanceschedule.dto;

import com.matuageorge.technicalmaintenanceschedule.model.TaskPriority;
import lombok.Data;

@Data
public class TaskDto {
    private String description;
    private TaskPriority priority;
    private Integer frequency;
}
