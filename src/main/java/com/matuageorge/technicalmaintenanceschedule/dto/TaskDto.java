package com.matuageorge.technicalmaintenanceschedule.dto;

import com.matuageorge.technicalmaintenanceschedule.model.TaskPriority;

public class TaskDto extends AbstractBaseDto {
    private String description;
    private TaskPriority priority;
    private Integer frequency;
}
