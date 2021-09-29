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
        Optional<Task> taskToSave = taskRepository.findById(taskDto.getId());
        if (taskToSave.isPresent()) {
            log.info("Task to save with such id already exists");
            throw new ResourceAlreadyExistsException("Task with such id already exists");
        } else {
            try {
                return taskRepository.save(modelMapper.map(taskDto, Task.class));
            } catch (IllegalArgumentException e) {
                throw new ValidationException("Task cannot be null");
            }
        }
    }

    @Override
    public Task update(TaskDto taskDto) {
        taskRepository.findById(taskDto.getId());
        log.info("Task to update is found");
        return taskRepository.save(modelMapper.map(taskDto, Task.class));
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
}