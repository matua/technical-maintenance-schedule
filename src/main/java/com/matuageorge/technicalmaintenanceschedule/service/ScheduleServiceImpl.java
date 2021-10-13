package com.matuageorge.technicalmaintenanceschedule.service;

import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.Schedule;
import com.matuageorge.technicalmaintenanceschedule.model.Task;
import com.matuageorge.technicalmaintenanceschedule.model.Terminal;
import com.matuageorge.technicalmaintenanceschedule.repository.ScheduleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Override
    public Schedule save(Schedule schedule) throws ValidationException, ResourceAlreadyExistsException {
        return scheduleRepository.save(schedule);
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
}
