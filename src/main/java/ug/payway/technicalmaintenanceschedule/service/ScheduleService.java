package ug.payway.technicalmaintenanceschedule.service;

import org.springframework.data.domain.Page;
import ug.payway.technicalmaintenanceschedule.dto.ScheduleDto;
import ug.payway.technicalmaintenanceschedule.exception.NotFoundException;
import ug.payway.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import ug.payway.technicalmaintenanceschedule.exception.ValidationException;
import ug.payway.technicalmaintenanceschedule.model.Schedule;
import ug.payway.technicalmaintenanceschedule.model.Task;
import ug.payway.technicalmaintenanceschedule.model.Terminal;

import java.util.List;

public interface ScheduleService {
    List<Schedule> findAllByTerminalAndTask(Terminal terminal, Task task);

    Schedule save(Schedule schedule) throws ValidationException, ResourceAlreadyExistsException;

    Schedule update(Schedule schedule) throws ValidationException, NotFoundException;

    void delete(Long taskId) throws ValidationException, NotFoundException;

    Schedule findById(Long id) throws ValidationException, NotFoundException;

    Page<ScheduleDto> findAll(Integer page, Integer pageSize) throws NotFoundException;

    List<ScheduleDto> findAll();

    List<ScheduleDto> findByEndExecutionDateTimeNotNull();

    List<Schedule> addUrgentSchedules() throws ValidationException, NotFoundException;

    void completeSchedule(Long scheduleId);

    void startSchedule(Long scheduleId);

    Page<ScheduleDto> findAllSortedByTaskPriority(Integer page, Integer pageSize) throws NotFoundException;

    Page<ScheduleDto> findAllSortedByTaskPriorityAndByEndExecutionDateTimeNull(Integer page, Integer pageSize) throws NotFoundException;

    Page<ScheduleDto> findAllSortedByTaskPriorityAndByEndExecutionDateTimeNullAndByUserEmail(Integer page,
                                                                                             Integer pageSize
            , String email) throws NotFoundException;

    void grabSchedule(Long scheduleId);

    void releaseSchedule(Long scheduleId);

    void setUser(Long scheduleId, Long userId);

    void setOptimizationIndex(Long scheduleId, Long optimizationIndex);

    Page<Schedule> findAllSortedByOptimizationIndexAndByEndExecutionDateTimeNullAndByUserEmail(Integer page, Integer pageSize, String email);

    List<ScheduleDto> findAllByTerminalAndTaskAndByEndExecutionDateTimeNull(Terminal terminal, Task task);
}
