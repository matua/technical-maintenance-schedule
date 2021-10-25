package com.matuageorge.technicalmaintenanceschedule.service;

import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MainMainPlannerServiceImpl implements MainPlannerService {

    @Value("${cron.schedule.timezone}")
    private String timeZone;
    @Value("${terminalType}")
    private String terminalType;
    private final TerminalService terminalService;
    private final TaskService taskService;
    private final ScheduleService scheduleService;
    private final UserService userService;

    //    @Scheduled(cron = "${cron.schedule}", zone = "${cron.schedule.timezone}")
    public void updateSchedule() throws NotFoundException, ValidationException, ResourceAlreadyExistsException {

        List<Terminal> terminals = terminalService.updateListOfTerminalsInDb(TerminalType.valueOf(terminalType));
        List<Task> tasks = taskService.findAll();
        User user = userService.findByEmail("kulmba@payway.ug");

        for (Terminal terminal : terminals) {
            for (Task task : tasks) {
                Schedule schedule = Schedule.builder()
                        .status(TaskStatus.SCHEDULED)
                        .terminal(terminal)
                        .task(task)
                        .user(user)
                        .build();
                Optional<Schedule> scheduleByTerminalAndTask = scheduleService.findByTerminalAndTask(terminal, task);
                if (scheduleByTerminalAndTask.isEmpty()) {
                    scheduleService.save(schedule);
                }
            }
        }
    }

    public void rescheduleCompletedRegularSchedules() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of(timeZone));
        List<Schedule> finishedSchedules = scheduleService.findByEndExecutionDateTimeNotNull();
        finishedSchedules.forEach(
                schedule -> {
                    LocalDateTime nextScheduledDay = schedule.getEndExecutionDateTime()
                            .plusDays(schedule.getTask().getFrequency());
                    if (nextScheduledDay.isAfter(now)) {
                        Schedule newSchedule = Schedule.builder()
                                .status(TaskStatus.SCHEDULED)
                                .terminal(schedule.getTerminal())
                                .task(schedule.getTask())
                                .user(schedule.getUser())
                                .build();
                        try {
                            scheduleService.save(newSchedule);
                        } catch (ValidationException | ResourceAlreadyExistsException e) {
                            log.error(e.getMessage());
                        }
                    }
                }
        );
    }
}