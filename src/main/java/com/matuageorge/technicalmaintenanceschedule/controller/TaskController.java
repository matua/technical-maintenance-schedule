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

@CrossOrigin
@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/admin")
    public ResponseEntity<Task> save(@RequestBody TaskDto taskDto) throws ValidationException, ResourceAlreadyExistsException {

        log.info("Handling task {} to create", taskDto);

        Task savedTask = taskService.save(taskDto);
        return ResponseEntity.ok(savedTask);
    }

    @PutMapping("/admin")
    public ResponseEntity<Task> update(@RequestBody TaskDto taskDto) throws ValidationException,
            ResourceAlreadyExistsException, NotFoundException {

        log.info("Handling task to update with task {}", taskDto);

        Task updatedTask = taskService.update(taskDto);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/admin/{taskId}")
    public ResponseEntity<Void> delete(@PathVariable Long taskId) throws ValidationException, NotFoundException {

        log.info("Handling delete task with taskId: {}", taskId);

        taskService.delete(taskId);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> findById(@PathVariable Long taskId) throws ValidationException, NotFoundException {

        log.info("Handling task to find by taskId: {}", taskId);

        Task taskResponseEntity = taskService.findById(taskId);
        return ResponseEntity.ok().body(taskResponseEntity);
    }

    @GetMapping("/{page}/{pageSize}")
    public ResponseEntity<Page<Task>> findAll(
            @PathVariable Integer page,
            @PathVariable Integer pageSize) throws NotFoundException, IllegalAccessException, NoSuchFieldException {

        log.info("Handling find all tasks page: {} with size: {}", page, pageSize);

        Page<Task> tasksPageResponseBody = taskService.findAll(page, pageSize);
        return ResponseEntity.ok().body(tasksPageResponseBody);
    }
}