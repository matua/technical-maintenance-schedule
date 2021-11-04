package com.matuageorge.technicalmaintenanceschedule.dto;

import com.matuageorge.technicalmaintenanceschedule.model.TaskStatus;
import com.matuageorge.technicalmaintenanceschedule.model.Terminal;

import java.time.LocalDateTime;

public class ScheduleDto {

    private Terminal terminal;
    private TaskStatus status;
    private LocalDateTime dateTimeCreated;
    private LocalDateTime startExecutionDateTime;
    private LocalDateTime endExecutionDateTime;
}
