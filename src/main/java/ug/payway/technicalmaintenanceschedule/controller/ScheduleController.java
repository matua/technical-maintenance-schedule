package ug.payway.technicalmaintenanceschedule.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ug.payway.technicalmaintenanceschedule.config.Utility;
import ug.payway.technicalmaintenanceschedule.dto.ScheduleDto;
import ug.payway.technicalmaintenanceschedule.exception.NotFoundException;
import ug.payway.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import ug.payway.technicalmaintenanceschedule.exception.ValidationException;
import ug.payway.technicalmaintenanceschedule.model.Schedule;
import ug.payway.technicalmaintenanceschedule.service.ScheduleService;

@CrossOrigin
@RestController
@Slf4j
@AllArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ModelMapper modelMapper;

    @PostMapping(Utility.ADMIN + Utility.SCHEDULES)
    public ResponseEntity<Schedule> save(@RequestBody ScheduleDto scheduleDto) throws ValidationException,
            ResourceAlreadyExistsException {

        log.info("Handling schedule {} to create", scheduleDto);

        return ResponseEntity.ok(scheduleService.save(modelMapper.map(scheduleDto, Schedule.class)));
    }

    @PutMapping(Utility.ADMIN + Utility.SCHEDULES)
    public ResponseEntity<Schedule> update(@RequestBody ScheduleDto scheduleDto) throws ValidationException,
            ResourceAlreadyExistsException, NotFoundException {

        log.info("Handling schedule to update with schedule {}", scheduleDto);

        Schedule updatedSchedule = scheduleService.update(modelMapper.map(scheduleDto, Schedule.class));
        return ResponseEntity.ok(updatedSchedule);
    }


    @PutMapping(Utility.SCHEDULES + "/start/{scheduleId}")
    public ResponseEntity<Void> startSchedule(@PathVariable Long scheduleId) {

        log.info("Handling schedule with id: {} to start..", scheduleId);

        scheduleService.startSchedule(scheduleId);
        return ResponseEntity.accepted().build();
    }

    @PutMapping(Utility.SCHEDULES + "/grab/{scheduleId}")
    public ResponseEntity<Void> grabSchedule(@PathVariable Long scheduleId,
                                             @AuthenticationPrincipal UserDetails userDetails) {

        log.info("Handling schedule grab with id: {} by user: {}",
                scheduleId,
                userDetails.getUsername());

        scheduleService.grabSchedule(scheduleId);
        return ResponseEntity.accepted().build();
    }

    @PutMapping(Utility.SCHEDULES + "/release/{scheduleId}")
    public ResponseEntity<Void> releaseSchedule(@PathVariable Long scheduleId,
                                                @AuthenticationPrincipal UserDetails userDetails) {

        log.info("Handling schedule release with id: {} by user: {}",
                scheduleId,
                userDetails.getUsername());

        scheduleService.releaseSchedule(scheduleId);
        return ResponseEntity.accepted().build();
    }

    @PutMapping(Utility.SCHEDULES + "/complete/{scheduleId}")
    public ResponseEntity<Void> completeSchedule(@PathVariable Long scheduleId) {

        log.info("Handling schedule with id: {} to complete..", scheduleId);

        scheduleService.completeSchedule(scheduleId);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping(Utility.ADMIN + Utility.SCHEDULES + "/{scheduleId}")
    public ResponseEntity<Void> delete(@PathVariable Long scheduleId) throws ValidationException,
            NotFoundException {

        log.info("Handling delete schedule with schedule id: {}", scheduleId);
        scheduleService.delete(scheduleId);
        return ResponseEntity.accepted().build();
    }

    @GetMapping(Utility.SCHEDULES + "/{scheduleId}")
    public ResponseEntity<Schedule> findById(@PathVariable Long scheduleId) throws ValidationException, NotFoundException {

        log.info("Handling task to find by scheduleId: {}", scheduleId);

        Schedule taskResponseEntity = scheduleService.findById(scheduleId);
        return ResponseEntity.ok().body(taskResponseEntity);
    }

    @GetMapping(Utility.SCHEDULES + "/{page}/{pageSize}")
    public ResponseEntity<Page<ScheduleDto>> findAll(
            @PathVariable Integer page,
            @PathVariable Integer pageSize) throws NotFoundException, IllegalAccessException, NoSuchFieldException {

        log.info("Handling find all schedules page: {} with size: {}", page, pageSize);

        Page<ScheduleDto> schedulesPageResponseBody = scheduleService.findAllSortedByTaskPriority(page, pageSize);
        return ResponseEntity.ok().body(schedulesPageResponseBody);
    }

    @GetMapping(Utility.ADMIN + Utility.SCHEDULES + "/notCompleted/{page}/{pageSize}")
    public ResponseEntity<Page<ScheduleDto>> findAllNotCompleted(
            @PathVariable Integer page,
            @PathVariable Integer pageSize) throws NotFoundException {

        log.info("Handling find all NOT COMPLETED schedules page: {} with size: {}", page, pageSize);

        Page<ScheduleDto> schedulesPageResponseBody =
                scheduleService.findAllSortedByTaskPriorityAndByEndExecutionDateTimeNull(page, pageSize);
        return ResponseEntity.ok().body(schedulesPageResponseBody);
    }

    @GetMapping(Utility.ADMIN + Utility.SCHEDULES + "/notCompletedByUser/{page}/{pageSize}")
    public ResponseEntity<Page<ScheduleDto>> findAllNotCompletedByUser(
            @PathVariable Integer page,
            @PathVariable Integer pageSize, @AuthenticationPrincipal UserDetails userDetails) throws NotFoundException {

        log.info("Handling for user:{} find all NOT COMPLETED schedules page: {} with size: {}", userDetails, page,
                pageSize);

        String email = userDetails.getUsername();

        Page<ScheduleDto> schedulesPageResponseBody =
                scheduleService.findAllSortedByTaskPriorityAndByEndExecutionDateTimeNullAndByUserEmail(page, pageSize,
                        email);
        return ResponseEntity.ok().body(schedulesPageResponseBody);
    }

    @GetMapping(Utility.SCHEDULES + "/notCompletedSortedByPriorityIndex/{page}/{pageSize}")
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