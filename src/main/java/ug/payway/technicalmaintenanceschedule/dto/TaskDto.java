package ug.payway.technicalmaintenanceschedule.dto;

import lombok.Data;
import ug.payway.technicalmaintenanceschedule.model.TaskPriority;

@Data
public class TaskDto {
    private String description;
    private TaskPriority priority;
    private Integer frequency;
    private Long messageId;
}
