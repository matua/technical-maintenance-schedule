package com.matuageorge.technicalmaintenanceschedule.service;

import com.matuageorge.technicalmaintenanceschedule.dto.TaskDto;
import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.Task;
import com.matuageorge.technicalmaintenanceschedule.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    TaskRepository taskRepository;
    ModelMapper modelMapper;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Task save(TaskDto taskDto) throws ValidationException, ResourceAlreadyExistsException {
        Optional<Task> taskToSave = taskRepository.findById(taskDto.getId());
        if (taskToSave.isEmpty()) {
            try {
                return taskRepository.save(modelMapper.map(taskDto, Task.class));
            } catch (IllegalArgumentException e) {
                throw new ValidationException("Task cannot be null");
            }
        } else {
            log.info("Task to save with such id already exists");
            throw new ResourceAlreadyExistsException("Task with such id already exists");
        }
    }

    @Override
    public Task update(TaskDto taskDto) throws NotFoundException {
        Optional<Task> taskToUpdate = taskRepository.findById(taskDto.getId());
        Task savedTask;
        if (taskToUpdate.isPresent()) {
            log.info("Task to update is found");
            savedTask = taskRepository.save(modelMapper.map(taskDto, Task.class));
        } else {
            log.info("Task to update is NOT found");
            throw new NotFoundException("Task to update is NOT found");
        }
        return savedTask;
    }

    @Override
    public void delete(Long taskId) throws NotFoundException {
        findById(taskId);
        try {
            taskRepository.deleteById(taskId);
        } catch (IllegalArgumentException e) {
            throw new NotFoundException("Task not found");
        }
    }

    @Override
    public Page<Task> findAll(Integer page, Integer pageSize) throws NotFoundException {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").descending());
        Page<Task> tasksPage = taskRepository.findAll(pageable);

        if (tasksPage.hasContent()) {
            return tasksPage;
        } else {
            throw new NotFoundException("No tasks found");
        }
    }

    @Override
    public Task findById(Long id) throws NotFoundException {
        Optional<Task> taskToFind = taskRepository.findById(id);
        if (taskToFind.isPresent()) {
            return taskToFind.get();
        } else {
            throw new NotFoundException("Task is not found");
        }
    }
}
