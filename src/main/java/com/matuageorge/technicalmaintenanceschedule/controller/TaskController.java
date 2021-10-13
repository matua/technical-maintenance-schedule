package com.matuageorge.technicalmaintenanceschedule.controller;

import com.matuageorge.technicalmaintenanceschedule.dto.TaskDto;
import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.Task;
import com.matuageorge.technicalmaintenanceschedule.service.TaskService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.matuageorge.technicalmaintenanceschedule.config.Utility.ADMIN;
import static com.matuageorge.technicalmaintenanceschedule.config.Utility.TASKS;

@CrossOrigin
@RestController
@Slf4j
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping(ADMIN + TASKS)
    public ResponseEntity<Task> save(@RequestBody TaskDto taskDto) throws ValidationException, ResourceAlreadyExistsException {

        log.info("Handling task {} to create", taskDto);

        return ResponseEntity.ok(taskService.save(taskDto));
    }

    @PutMapping(ADMIN + TASKS)
    public ResponseEntity<Task> update(@RequestBody TaskDto taskDto) throws ValidationException,
            ResourceAlreadyExistsException, NotFoundException {

        log.info("Handling task to update with task {}", taskDto);

        Task updatedTask = taskService.update(taskDto);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping(ADMIN + TASKS + "/{taskDescription}")
    public ResponseEntity<Void> delete(@PathVariable String taskDescription) throws ValidationException,
            NotFoundException {

        log.info("Handling delete task with task description: {}", taskDescription);
        taskService.deleteByDescription(taskDescription);
        return ResponseEntity.accepted().build();
    }

    @GetMapping(TASKS + "/{taskId}")
    public ResponseEntity<Task> findById(@PathVariable Long taskId) throws ValidationException, NotFoundException {

        log.info("Handling task to find by taskId: {}", taskId);

        Task taskResponseEntity = taskService.findById(taskId);
        return ResponseEntity.ok().body(taskResponseEntity);
    }

    @GetMapping(TASKS + "/{page}/{pageSize}")
    public ResponseEntity<Page<Task>> findAll(
            @PathVariable Integer page,
            @PathVariable Integer pageSize) throws NotFoundException, IllegalAccessException, NoSuchFieldException {

        log.info("Handling find all tasks page: {} with size: {}", page, pageSize);

        Page<Task> tasksPageResponseBody = taskService.findAllByPage(page, pageSize);
        return ResponseEntity.ok().body(tasksPageResponseBody);
    }
}