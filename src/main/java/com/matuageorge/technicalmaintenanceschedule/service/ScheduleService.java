package com.matuageorge.technicalmaintenanceschedule.service;

import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.Schedule;
import com.matuageorge.technicalmaintenanceschedule.model.Task;
import com.matuageorge.technicalmaintenanceschedule.model.TaskPriority;
import com.matuageorge.technicalmaintenanceschedule.model.Terminal;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ScheduleService {
    List<Schedule> findAllByTerminalAndTask(Terminal terminal, Task task);

    Schedule save(Schedule schedule) throws ValidationException, ResourceAlreadyExistsException;

    Schedule update(Schedule schedule) throws ValidationException, NotFoundException;

    void delete(Long taskId) throws ValidationException, NotFoundException;

    Schedule findById(Long id) throws ValidationException, NotFoundException;

    Page<Schedule> findAll(Integer page, Integer pageSize) throws NotFoundException;

    List<Schedule> findAll();

    List<Schedule> findByEndExecutionDateTimeNotNull();

    List<Schedule> addUrgentSchedules() throws ValidationException, NotFoundException;

    void completeSchedule(Long scheduleId);

    void startSchedule(Long scheduleId);

    Page<Schedule> findByEndExecutionDateTimeNull(Integer page, Integer pageSize) throws NotFoundException;

    Page<Schedule> findAllSortedByTaskPriority(Integer page, Integer pageSize) throws NotFoundException;

    Page<Schedule> findAllSortedByTaskPriorityAndByEndExecutionDateTimeNull(Integer page, Integer pageSize) throws NotFoundException;

    Page<Schedule> findAllSortedByTaskPriorityAndByEndExecutionDateTimeNullAndByUserEmail(Integer page, Integer pageSize
            , String email) throws NotFoundException;

    List<Schedule> findAllByTaskPriority(TaskPriority urgent);

    void grabSchedule(Long scheduleId);

    void releaseSchedule(Long scheduleId);

    void setUser(Long scheduleId, Long userId);

    void setOptimizationIndex(Long scheduleId, Long optimizationIndex);

    Page<Schedule> findAllSortedByOptimizationIndexAndByEndExecutionDateTimeNullAndByUserEmail(Integer page, Integer pageSize, String email);

    List<Schedule> findAllByTerminalAndTaskAndByEndExecutionDateTimeNull(Terminal terminal, Task task);
}
