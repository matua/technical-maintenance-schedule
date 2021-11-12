package com.matuageorge.technicalmaintenanceschedule.controller;

import com.matuageorge.technicalmaintenanceschedule.dto.ScheduleDto;
import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.Schedule;
import com.matuageorge.technicalmaintenanceschedule.service.ScheduleService;
import com.matuageorge.technicalmaintenanceschedule.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static com.matuageorge.technicalmaintenanceschedule.config.Utility.ADMIN;
import static com.matuageorge.technicalmaintenanceschedule.config.Utility.SCHEDULES;

@CrossOrigin
@RestController
@Slf4j
@AllArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping(ADMIN + SCHEDULES)
    public ResponseEntity<Schedule> save(@RequestBody ScheduleDto scheduleDto) throws ValidationException,
            ResourceAlreadyExistsException {

        log.info("Handling schedule {} to create", scheduleDto);

        return ResponseEntity.ok(scheduleService.save(modelMapper.map(scheduleDto, Schedule.class)));
    }

    @PutMapping(ADMIN + SCHEDULES)
    public ResponseEntity<Schedule> update(@RequestBody ScheduleDto scheduleDto) throws ValidationException,
            ResourceAlreadyExistsException, NotFoundException {

        log.info("Handling schedule to update with schedule {}", scheduleDto);

        Schedule updatedSchedule = scheduleService.update(modelMapper.map(scheduleDto, Schedule.class));
        return ResponseEntity.ok(updatedSchedule);
    }


    @PutMapping(SCHEDULES + "/start/{scheduleId}")
    public ResponseEntity<Void> startSchedule(@PathVariable Long scheduleId) {

        log.info("Handling schedule with id: {} to start..", scheduleId);

        scheduleService.startSchedule(scheduleId);
        return ResponseEntity.accepted().build();
    }

    @PutMapping(SCHEDULES + "/grab/{scheduleId}")
    public ResponseEntity<Void> grabSchedule(@PathVariable Long scheduleId,
                                             @AuthenticationPrincipal UserDetails userDetails) {

        log.info("Handling schedule grab with id: {} by user: {}",
                scheduleId,
                userDetails.getUsername());

        scheduleService.grabSchedule(scheduleId);
        return ResponseEntity.accepted().build();
    }

    @PutMapping(SCHEDULES + "/release/{scheduleId}")
    public ResponseEntity<Void> releaseSchedule(@PathVariable Long scheduleId,
                                                @AuthenticationPrincipal UserDetails userDetails) {

        log.info("Handling schedule release with id: {} by user: {}",
                scheduleId,
                userDetails.getUsername());

        scheduleService.releaseSchedule(scheduleId);
        return ResponseEntity.accepted().build();
    }

    @PutMapping(SCHEDULES + "/complete/{scheduleId}")
    public ResponseEntity<Void> completeSchedule(@PathVariable Long scheduleId) {

        log.info("Handling schedule with id: {} to complete..", scheduleId);

        scheduleService.completeSchedule(scheduleId);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping(ADMIN + SCHEDULES + "/{scheduleId}")
    public ResponseEntity<Void> delete(@PathVariable Long scheduleId) throws ValidationException,
            NotFoundException {

        log.info("Handling delete schedule with schedule id: {}", scheduleId);
        scheduleService.delete(scheduleId);
        return ResponseEntity.accepted().build();
    }

    @GetMapping(SCHEDULES + "/{scheduleId}")
    public ResponseEntity<Schedule> findById(@PathVariable Long scheduleId) throws ValidationException, NotFoundException {

        log.info("Handling task to find by scheduleId: {}", scheduleId);

        Schedule taskResponseEntity = scheduleService.findById(scheduleId);
        return ResponseEntity.ok().body(taskResponseEntity);
    }

    @GetMapping(SCHEDULES + "/{page}/{pageSize}")
    public ResponseEntity<Page<Schedule>> findAll(
            @PathVariable Integer page,
            @PathVariable Integer pageSize) throws NotFoundException, IllegalAccessException, NoSuchFieldException {

        log.info("Handling find all schedules page: {} with size: {}", page, pageSize);

        Page<Schedule> schedulesPageResponseBody = scheduleService.findAllSortedByTaskPriority(page, pageSize);
        return ResponseEntity.ok().body(schedulesPageResponseBody);
    }

    @GetMapping(ADMIN + SCHEDULES + "/notCompleted/{page}/{pageSize}")
    public ResponseEntity<Page<Schedule>> findAllNotCompleted(
            @PathVariable Integer page,
            @PathVariable Integer pageSize) throws NotFoundException {

        log.info("Handling find all NOT COMPLETED schedules page: {} with size: {}", page, pageSize);

        Page<Schedule> schedulesPageResponseBody =
                scheduleService.findAllSortedByTaskPriorityAndByEndExecutionDateTimeNull(page, pageSize);
        return ResponseEntity.ok().body(schedulesPageResponseBody);
    }

    @GetMapping(ADMIN + SCHEDULES + "/notCompletedByUser/{page}/{pageSize}")
    public ResponseEntity<Page<Schedule>> findAllNotCompletedByUser(
            @PathVariable Integer page,
            @PathVariable Integer pageSize, @AuthenticationPrincipal UserDetails userDetails) throws NotFoundException {

        log.info("Handling for user:{} find all NOT COMPLETED schedules page: {} with size: {}", userDetails, page,
                pageSize);

        String email = userDetails.getUsername();

        Page<Schedule> schedulesPageResponseBody =
                scheduleService.findAllSortedByTaskPriorityAndByEndExecutionDateTimeNullAndByUserEmail(page, pageSize,
                        email);
        return ResponseEntity.ok().body(schedulesPageResponseBody);
    }

    @GetMapping(SCHEDULES + "/notCompletedSortedByPriorityIndex/{page}/{pageSize}")
    public ResponseEntity<Page<Schedule>> findAllNotCompletedByPriorityIndexByUser(
            @PathVariable Integer page,
            @PathVariable Integer pageSize, @AuthenticationPrincipal UserDetails userDetails) throws NotFoundException {

        log.info("Handling for user:{} find all NOT COMPLETED schedules SORTED BY OPTIMIZATION INDEX page: {} with " +
                        "size: {}",
                userDetails, page,
                pageSize);

        String email = userDetails.getUsername();

        Page<Schedule> schedulesPageResponseBody =
                scheduleService.findAllSortedByOptimizationIndexAndByEndExecutionDateTimeNullAndByUserEmail(page, pageSize,
                        email);
        return ResponseEntity.ok().body(schedulesPageResponseBody);
    }
}