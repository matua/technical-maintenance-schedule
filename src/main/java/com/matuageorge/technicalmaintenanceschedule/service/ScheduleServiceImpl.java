package com.matuageorge.technicalmaintenanceschedule.service;

import com.matuageorge.technicalmaintenanceschedule.dto.TaskDto;
import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.*;
import com.matuageorge.technicalmaintenanceschedule.repository.ScheduleRepository;
import com.matuageorge.technicalmaintenanceschedule.service.api.PayWayApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final PayWayApiService payWayApiService;
    private final UserService userService;
    private final ScheduleRepository scheduleRepository;
    private final TerminalService terminalService;
    private final TaskService taskService;
    private final ModelMapper modelMapper;
    @Value("${errorMessagesReportWindow}")
    String errorMessagesReportWindow;
    @Value("${cron.schedule.timezone}")
    private String timeZone;

    @Override
    public Schedule save(Schedule schedule) throws ValidationException, ResourceAlreadyExistsException {
        return scheduleRepository.save(schedule);
    }

    @Override
    public List<Schedule> addUrgentSchedules() throws ValidationException, NotFoundException {

        List<Schedule> urgentSchedulesToAdd = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd+HH:mm");
        LocalDateTime now = LocalDateTime.now(ZoneId.of(timeZone));
        String startTimeStamp = getStartTimeStamp(formatter, now, errorMessagesReportWindow);
        String endTimeStamp = getEndTimeStamp(formatter, now);
        User user = userService.findByEmail("kulmba@payway.ug");

        Optional<Map<String, String>> optionalOfAllTerminalsToBeUrgentlyServiced = payWayApiService
                .getAllTerminalsToBeUrgentlyServiced(startTimeStamp, endTimeStamp);

        if (optionalOfAllTerminalsToBeUrgentlyServiced.isPresent()) {
            final Map<String, String> allTerminalsToBeUrgentlyServiced = optionalOfAllTerminalsToBeUrgentlyServiced.get();
            allTerminalsToBeUrgentlyServiced.forEach((term, error) -> {
                Task task = Task.builder()
                        .description(error)
                        .frequency(0)
                        .priority(TaskPriority.URGENT)
                        .build();
                try {
                    taskService.save(modelMapper.map(task, TaskDto.class));
                } catch (ValidationException | ResourceAlreadyExistsException e) {
                    e.printStackTrace();
                }

                terminalService.findByName(term).ifPresent(
                        t -> taskService.findByDescription(task.getDescription()).ifPresent(
                                newTask -> {
                                    Schedule schedule = Schedule.builder()
                                            .status(TaskStatus.SCHEDULED)
                                            .terminal(t)
                                            .task(newTask)
                                            .user(user)
                                            .build();
                                    try {
                                        save(schedule);
                                        urgentSchedulesToAdd.add(schedule);
                                    } catch (ValidationException | ResourceAlreadyExistsException e) {
                                        log.error("Cannot add schedule with URGENT task. Error: {}", e.getLocalizedMessage());
                                    }
                                }
                        )
                );
            });
        }
        if (urgentSchedulesToAdd.isEmpty()) {
            log.info("No URGENT schedules were added");
        }
        urgentSchedulesToAdd.forEach(schedule -> log.info("Urgent schedule added for TERM: {} with ERROR:{}",
                schedule.getTerminal().getName(), schedule.getTask().getDescription()));
        return urgentSchedulesToAdd;
    }

    @Override
    public Schedule update(Schedule schedule) throws ValidationException, NotFoundException {
        Optional<Schedule> optionalScheduleToUpdate = scheduleRepository.findById(schedule.getId());
        Schedule savedSchedule;
        if (optionalScheduleToUpdate.isPresent()) {
            log.info("Schedule to update is found");
            savedSchedule = scheduleRepository.save(schedule);
        } else {
            log.info("Schedule to update is NOT found");
            throw new NotFoundException("Schedule to update is NOT found");
        }
        return savedSchedule;
    }

    @Override
    public void delete(Long scheduleId) throws ValidationException, NotFoundException {
        findById(scheduleId);
        scheduleRepository.deleteById(scheduleId);
    }

    @Override
    public Schedule findById(Long id) throws ValidationException, NotFoundException {
        return scheduleRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Schedule with id: %s not found", id)));
    }

    @Override
    public Page<Schedule> findAll(Integer page, Integer pageSize) throws NotFoundException {

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("date_time_created").ascending());
        Page<Schedule> tasksPage = scheduleRepository.findAll(pageable);

        if (tasksPage.hasContent()) {
            return tasksPage;
        } else {
            throw new NotFoundException("No schedule found");
        }
    }

    @Override
    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    @Override
    public List<Schedule> findByEndExecutionDateTimeNotNull() {
        return scheduleRepository.findByEndExecutionDateTimeNotNull();
    }

    @Override
    public Optional<Schedule> findByTerminalAndTask(Terminal terminal, Task task) {
        return scheduleRepository.findByTerminalAndTask(terminal, task);
    }

    private String getEndTimeStamp(DateTimeFormatter formatter, LocalDateTime now) {
        return now.atZone(ZoneId.of("UTC")).format(formatter);
    }

    private String getStartTimeStamp(DateTimeFormatter formatter, LocalDateTime now, String errorMessagesReportWindow) {
        return now.atZone(ZoneId.of("UTC")).minusMinutes(
                Integer.parseInt(errorMessagesReportWindow)).format(formatter);
    }
}