package com.matuageorge.technicalmaintenanceschedule.service;

import com.matuageorge.technicalmaintenanceschedule.dto.TaskDto;
import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.Task;
import org.springframework.data.domain.Page;

public class TaskServiceImpl implements TaskService {
    @Override
    public Task save(TaskDto taskDto) throws ValidationException, ResourceAlreadyExistsException {
        return null;
    }

    @Override
    public Task update(Long productId, TaskDto taskDto) throws ValidationException, NotFoundException {
        return null;
    }

    @Override
    public void delete(Long productId) throws ValidationException, NotFoundException {

    }

    @Override
    public Page<Task> findAll(Integer page, Integer pageSize) throws NotFoundException {
        return null;
    }

    @Override
    public Task findById(Long id) throws ValidationException, NotFoundException {
        return null;
    }
}
