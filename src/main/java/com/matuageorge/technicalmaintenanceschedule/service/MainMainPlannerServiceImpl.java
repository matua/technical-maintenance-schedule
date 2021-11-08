package com.matuageorge.technicalmaintenanceschedule.service;

import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.*;
import com.matuageorge.technicalmaintenanceschedule.service.api.routing.DirectionsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
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
    private final DirectionsService googleMapsDirectionsService;
    @Value("${payway.location.lat.headoffice}")
    private Double headOfficeLatitude;
    private final TerminalService terminalService;
    private final TaskService taskService;
    private final ScheduleService scheduleService;
    private final UserService userService;
    @Value("${payway.location.long.headoffice}")
    private Double headOfficeLongitude;

    @Scheduled(cron = "${cron.schedule}", zone = "${cron.schedule.timezone}")
    public void addNewCommonTaskSchedulesIfExist() {

        List<Terminal> terminals = terminalService.findAll();

        List<Task> allCommonTasks = taskService.findAllCommon(TaskPriority.COMMON);

        terminals.forEach(
                terminal -> allCommonTasks.forEach(
                        task -> {
                            Optional<Schedule> scheduleByTerminalAndTask = scheduleService.findByTerminalAndTask(terminal, task);
                            if (scheduleByTerminalAndTask.isEmpty()) {
                                Schedule schedule = Schedule.builder()
                                        .status(TaskStatus.SCHEDULED)
                                        .terminal(terminal)
                                        .task(task)
                                        .build();
                                try {
                                    scheduleService.save(schedule);
                                } catch (ValidationException | ResourceAlreadyExistsException e) {
                                    log.error(e.getLocalizedMessage());
                                }
                            }
                        }
                ));
    }

    public void createNewSchedulesForCommonTasksDueAgain() {
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
                                .build();
                        try {
                            scheduleService.save(newSchedule);
                        } catch (ValidationException | ResourceAlreadyExistsException e) {
                            log.error(e.getLocalizedMessage());
                        }
                    }
                }
        );
    }

    @Override
    public List<Schedule> addUrgentSchedules() throws ValidationException, NotFoundException {
        return scheduleService.addUrgentSchedules();
    }

    @Override
    public void allocateCommonSchedulesBetweenUsers(int numberOfSchedules, int numberOfUsers) {

    }
}