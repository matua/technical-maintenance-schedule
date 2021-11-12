package com.matuageorge.technicalmaintenanceschedule.service;

import com.matuageorge.technicalmaintenanceschedule.dto.TaskDto;
import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.*;
import com.matuageorge.technicalmaintenanceschedule.repository.ScheduleRepository;
import com.matuageorge.technicalmaintenanceschedule.service.api.payway.PayWayApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private static final String NO_SCHEDULE_FOUND = "No schedule found";
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
//        startTimeStamp = getStartTimeStamp(formatter, now, "1440");
//        endTimeStamp = getStartTimeStamp(formatter, now, "0");

        final Optional<List<KioskMessage>> optionalOfAllTerminalsToBeUrgentlyServiced = payWayApiService
                .getAllTerminalsToBeUrgentlyServiced(startTimeStamp, endTimeStamp);

        if (optionalOfAllTerminalsToBeUrgentlyServiced.isPresent()) {
            final List<KioskMessage> allKioskMessagesWhereTerminalsMustBeUrgentlyServiced =
                    optionalOfAllTerminalsToBeUrgentlyServiced.get();
            var savedTaskReference = new Object() {
                Task savedTask;
            };
            allKioskMessagesWhereTerminalsMustBeUrgentlyServiced.forEach(
                    message -> {
                        Task task = Task.builder()
                                .description(beautifyArgs(message.getArgs()))
                                .frequency(0)
                                .messageId(message.getMessageId())
                                .priority(TaskPriority.URGENT)
                                .build();
                        try {
                            savedTaskReference.savedTask = taskService.save(modelMapper.map(task, TaskDto.class));
                            log.info("Saved new task:\n {}", savedTaskReference.savedTask.getDescription());
                        } catch (ValidationException | ResourceAlreadyExistsException e) {
                            savedTaskReference.savedTask = null;
                            log.error(e.getMessage());
                        }

                        if (savedTaskReference.savedTask != null) {
                            terminalService.findByName(message.getKiosk()).ifPresent(
                                    t -> {
                                        Schedule schedule = Schedule.builder()
                                                .status(TaskStatus.SCHEDULED)
                                                .terminal(t)
                                                .task(savedTaskReference.savedTask)
                                                .build();
                                        try {
                                            save(schedule);
                                            urgentSchedulesToAdd.add(schedule);
                                        } catch (ValidationException | ResourceAlreadyExistsException e) {
                                            log.error("Cannot add schedule with URGENT task. Error: {}", e.getLocalizedMessage());
                                        }
                                    }
                            );
                        }
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
    @Transactional
    public void completeSchedule(Long scheduleId) {
        scheduleRepository.completeSchedule(scheduleId, LocalDateTime.now());
    }

    @Override
    @Transactional
    public void startSchedule(Long scheduleId) {
        scheduleRepository.startSchedule(scheduleId, LocalDateTime.now());
    }

    @Override
    @Transactional
    public void grabSchedule(Long scheduleId) {
        scheduleRepository.grabSchedule(scheduleId, LocalDateTime.now());
    }

    @Override
    @Transactional
    public void releaseSchedule(Long scheduleId) {
        scheduleRepository.releaseSchedule(scheduleId, LocalDateTime.now());
    }

    @Override
    @Transactional
    public void setUser(Long scheduleId, Long userId) {
        scheduleRepository.setUser(scheduleId, userId);
    }

    @Override
    @Transactional
    public void setOptimizationIndex(Long scheduleId, Long optimizationIndex) {
        scheduleRepository.setOptimizationIndex(scheduleId, optimizationIndex);
    }

    @Override
    public Page<Schedule> findAllSortedByOptimizationIndexAndByEndExecutionDateTimeNullAndByUserEmail(
            Integer page, Integer pageSize, String email) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("optimizationIndex"));
        return scheduleRepository.findAllSortedByOptimizationIndexAndByEndExecutionDateTimeNullAndByUserEmail(
                pageable, email);
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

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("dateTimeCreated").descending());
        Page<Schedule> tasksPage = scheduleRepository.findAll(pageable);

        if (tasksPage.hasContent()) {
            return tasksPage;
        } else {
            throw new NotFoundException(NO_SCHEDULE_FOUND);
        }
    }

    @Override
    public Page<Schedule> findAllSortedByTaskPriority(Integer page, Integer pageSize) throws NotFoundException {

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Schedule> schedulePage = scheduleRepository.findAllByOrderByTaskPriorityDesc(pageable);

        if (schedulePage.hasContent()) {
            return schedulePage;
        } else {
            throw new NotFoundException(NO_SCHEDULE_FOUND);
        }
    }

    @Override
    public Page<Schedule> findAllSortedByTaskPriorityAndByEndExecutionDateTimeNull(Integer page, Integer pageSize) throws NotFoundException {

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Schedule> schedulePage = scheduleRepository.findAllSortedByTaskPriorityAndByEndExecutionDateTimeNull(pageable);

        if (schedulePage.hasContent()) {
            return schedulePage;
        } else {
            throw new NotFoundException(NO_SCHEDULE_FOUND);
        }
    }

    @Override
    public Page<Schedule> findAllSortedByTaskPriorityAndByEndExecutionDateTimeNullAndByUserEmail(
            Integer page, Integer pageSize, String email) throws NotFoundException {

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("optimizationIndex"));

        Page<Schedule> schedulePage =
                scheduleRepository.findAllSortedByTaskPriorityAndByEndExecutionDateTimeNullAndByEmail(pageable, email);

        if (schedulePage.hasContent()) {
            return schedulePage;
        } else {
            throw new NotFoundException(NO_SCHEDULE_FOUND);
        }
    }

    @Override
    public List<Schedule> findAllByTaskPriority(TaskPriority priority) {
        return scheduleRepository.findAllByTaskPriority(priority);
    }

    @Override
    public Page<Schedule> findByEndExecutionDateTimeNull(Integer page, Integer pageSize) throws NotFoundException {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("dateTimeCreated").descending());
        Page<Schedule> tasksPage = scheduleRepository.findByEndExecutionDateTimeNull(pageable);

        if (tasksPage.hasContent()) {
            return tasksPage;
        } else {
            throw new NotFoundException(NO_SCHEDULE_FOUND);
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
        final Optional<Schedule> byTerminalAndTask = scheduleRepository.findByTerminalAndTask(terminal, task);
        byTerminalAndTask.ifPresent(s -> log.info("error: {},{}",
                s.getTerminal().getName(),
                s.getTask().getDescription()));
        return byTerminalAndTask;
    }

    private String getEndTimeStamp(DateTimeFormatter formatter, LocalDateTime now) {
        return now.atZone(ZoneId.of("UTC")).format(formatter);
    }

    private String getStartTimeStamp(DateTimeFormatter formatter, LocalDateTime now, String errorMessagesReportWindow) {
        return now.atZone(ZoneId.of("UTC")).minusMinutes(
                Integer.parseInt(errorMessagesReportWindow)).format(formatter);
    }

    private String beautifyArgs(Map<String, String> args) {
        StringBuilder beautifiedArgs = new StringBuilder();
        args.forEach(
                (key, value) -> beautifiedArgs
                        .append(key)
                        .append(":")
                        .append(value)
                        .append(System.lineSeparator()));
        return String.valueOf(beautifiedArgs);
    }
}
