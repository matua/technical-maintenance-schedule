package com.matuageorge.technicalmaintenanceschedule.dto;

import com.matuageorge.technicalmaintenanceschedule.model.Task;
import com.matuageorge.technicalmaintenanceschedule.model.TaskStatus;
import com.matuageorge.technicalmaintenanceschedule.model.Terminal;
import com.matuageorge.technicalmaintenanceschedule.model.User;

import java.time.LocalDateTime;

public class ScheduleDto {

    private Terminal terminal;
    private Task task;
    private User user;
    private TaskStatus status;
    private LocalDateTime dateTimeCreated;
    private LocalDateTime startExecutionDateTime;
    private LocalDateTime endExecutionDateTime;
}
