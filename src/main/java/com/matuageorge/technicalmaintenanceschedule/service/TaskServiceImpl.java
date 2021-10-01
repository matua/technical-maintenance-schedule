package com.matuageorge.technicalmaintenanceschedule.service;

import com.matuageorge.technicalmaintenanceschedule.dto.TaskDto;
import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.Task;
import com.matuageorge.technicalmaintenanceschedule.repository.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    @Override
    public Task save(TaskDto taskDto) throws ValidationException, ResourceAlreadyExistsException {
        Optional<Task> taskToSave = taskRepository.findByDescription(taskDto.getDescription());
        if (taskToSave.isPresent()) {
            log.info("Task to save with such description already exists");
            throw new ResourceAlreadyExistsException("Task with such description already exists");
        } else {
            try {
                return taskRepository.save(modelMapper.map(taskDto, Task.class));
            } catch (IllegalArgumentException e) {
                throw new ValidationException("Task cannot be null");
            }
        }
    }

    @Override
    public Task update(TaskDto taskDto) throws NotFoundException {
        Optional<Task> taskToUpdate = taskRepository.findByDescription(taskDto.getDescription());
        Task savedTask;
        if (taskToUpdate.isPresent()) {
            Task task = taskToUpdate.get();
            log.info("Task to update is found");
            task.setDescription(taskDto.getDescription());
            task.setFrequency(taskDto.getFrequency());
            task.setPriority(taskDto.getPriority());
            savedTask = taskRepository.save(modelMapper.map(task, Task.class));
        } else {
            log.info("Task to update is NOT found");
            throw new NotFoundException("Task to update is NOT found");
        }
        return savedTask;
    }

    @Override
    public void delete(Long taskId) throws NotFoundException {
        findById(taskId);
        taskRepository.deleteById(taskId);
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
        return taskRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Task with id: %s not found", id)));
    }

    @Override
    public Optional<Task> findByDescription(String taskDescription) {
        return taskRepository.findByDescription(taskDescription);
    }

    @Override
    public void deleteByDescription(String taskDescription) throws NotFoundException {
        Task task = findByDescription(taskDescription).orElseThrow(
                () -> new NotFoundException("Task to delete is not found"));
        delete(task.getId());
    }
}