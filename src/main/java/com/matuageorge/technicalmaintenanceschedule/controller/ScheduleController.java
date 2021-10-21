package com.matuageorge.technicalmaintenanceschedule.controller;

import com.matuageorge.technicalmaintenanceschedule.dto.ScheduleDto;
import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.Schedule;
import com.matuageorge.technicalmaintenanceschedule.service.ScheduleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.matuageorge.technicalmaintenanceschedule.config.Utility.ADMIN;
import static com.matuageorge.technicalmaintenanceschedule.config.Utility.SCHEDULES;

@CrossOrigin
@RestController
@Slf4j
@AllArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
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

        log.info("Handling task to update with task {}", scheduleDto);

        Schedule updatedSchedule = scheduleService.update(modelMapper.map(scheduleDto, Schedule.class));
        return ResponseEntity.ok(updatedSchedule);
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

        Page<Schedule> schedulesPageResponseBody = scheduleService.findAll(page, pageSize);
        return ResponseEntity.ok().body(schedulesPageResponseBody);
    }
}