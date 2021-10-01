package com.matuageorge.technicalmaintenanceschedule.service;

import com.matuageorge.technicalmaintenanceschedule.dto.TaskDto;
import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.Task;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface TaskService {
    Task save(TaskDto taskDto) throws ValidationException, ResourceAlreadyExistsException;

    Task update(TaskDto taskDto) throws ValidationException, NotFoundException;

    void delete(Long taskId) throws ValidationException, NotFoundException;

    Page<Task> findAll(Integer page, Integer pageSize) throws NotFoundException;

    Task findById(Long id) throws ValidationException, NotFoundException;

    Optional<Task> findByDescription(String taskDescription);

    void deleteByDescription(String taskDescription) throws NotFoundException;
}
