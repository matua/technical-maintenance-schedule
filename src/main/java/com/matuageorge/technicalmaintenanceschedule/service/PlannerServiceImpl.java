package com.matuageorge.technicalmaintenanceschedule.service;

import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class PlannerServiceImpl implements PlannerService {

    //    @Value("${cron.schedule.timezone}")
//    private String timeZone;
    private final TerminalService terminalService;
    private final TaskService taskService;
    private final ScheduleService scheduleService;
    private final UserService userService;
//    @Value("${terminalType}")
//    private final TerminalType terminalType;

    //    @Scheduled(cron = "${cron.schedule}", zone = "${cron.schedule.timezone}")
    public void updateSchedule() throws NotFoundException, ValidationException, ResourceAlreadyExistsException {

        List<Terminal> terminals = terminalService.updateListOfTerminalsInDb(TerminalType.HARDWARE);
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

    public void rescheduleDone() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Africa/Kampala"));
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