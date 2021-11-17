package ug.payway.technicalmaintenanceschedule.dto;

import lombok.Data;
import ug.payway.technicalmaintenanceschedule.model.Task;
import ug.payway.technicalmaintenanceschedule.model.TaskStatus;
import ug.payway.technicalmaintenanceschedule.model.Terminal;
import ug.payway.technicalmaintenanceschedule.model.User;

import java.time.LocalDateTime;

@Data
public class ScheduleDto {
    private Long id;
    private Terminal terminal;
    private Task task;
    private User user;
    private TaskStatus status;
    private LocalDateTime dateTimeCreated;
    private LocalDateTime startExecutionDateTime;
    private LocalDateTime endExecutionDateTime;
    private LocalDateTime grabbedExecutionDateTime;
    private LocalDateTime releasedExecutionDateTime;
    private Long optimizationIndex;
}
