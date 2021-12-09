package ug.payway.technicalmaintenanceschedule.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ug.payway.technicalmaintenanceschedule.dto.ScheduleDto;
import ug.payway.technicalmaintenanceschedule.exception.NotFoundException;
import ug.payway.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import ug.payway.technicalmaintenanceschedule.exception.ValidationException;
import ug.payway.technicalmaintenanceschedule.model.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MainMainPlannerServiceImpl implements MainPlannerService {

  private final TerminalService terminalService;
  private final TaskService taskService;
  private final ScheduleService scheduleService;

  @Value("${cron.schedule.timezone}")
  private String timeZone;

  public void addNewCommonTaskSchedulesIfExist() {

    List<Terminal> terminals = terminalService.findAll();

    List<Task> allCommonTasks = taskService.findAllCommon(TaskPriority.COMMON);

    terminals.forEach(
        terminal ->
            allCommonTasks.forEach(
                task -> {
                  final List<Schedule> scheduleByTerminalAndTask =
                      scheduleService.findAllByTerminalAndTask(terminal, task);
                  if (scheduleByTerminalAndTask.isEmpty()) {
                    Schedule schedule =
                        Schedule.builder()
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
                }));
  }

  public void createNewSchedulesForCommonTasksDueAgain() {
    LocalDateTime now = LocalDateTime.now(ZoneId.of(timeZone));
    List<ScheduleDto> finishedSchedules = scheduleService.findByEndExecutionDateTimeNotNull();
    finishedSchedules.forEach(
        schedule -> {
          if (scheduleService
              .findAllByTerminalAndTaskAndByEndExecutionDateTimeNull(
                  schedule.getTerminal(), schedule.getTask())
              .isEmpty()) {
            LocalDateTime nextScheduledDay =
                schedule.getEndExecutionDateTime().plusDays(schedule.getTask().getFrequency());
            if (nextScheduledDay.isAfter(now)) {
              Schedule newSchedule =
                  Schedule.builder()
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
        });
  }

  @Override
  public List<Schedule> addUrgentSchedules() throws ValidationException, NotFoundException {
    return scheduleService.addUrgentSchedules();
  }
}
