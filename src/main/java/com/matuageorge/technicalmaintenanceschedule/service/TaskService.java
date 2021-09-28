package com.matuageorge.technicalmaintenanceschedule.service;

import com.matuageorge.technicalmaintenanceschedule.dto.TaskDto;
import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskService {
    Task save(TaskDto taskDto) throws ValidationException, ResourceAlreadyExistsException;

    Task update(Long productId, TaskDto taskDto) throws ValidationException, NotFoundException;

    void delete(Long productId) throws ValidationException, NotFoundException;

    Page<Task> findAll(Integer page, Integer pageSize) throws NotFoundException;

    Task findById(Long id) throws ValidationException, NotFoundException;
}
