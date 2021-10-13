package com.matuageorge.technicalmaintenanceschedule.service;

import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.Schedule;
import org.springframework.data.domain.Page;

public interface ScheduleService {
    Schedule save(Schedule schedule) throws ValidationException, ResourceAlreadyExistsException;

    Schedule update(Schedule schedule) throws ValidationException, NotFoundException;

    void delete(Long taskId) throws ValidationException, NotFoundException;

    Schedule findById(Long id) throws ValidationException, NotFoundException;

    Page<Schedule> findAll(Integer page, Integer pageSize) throws NotFoundException;

}
