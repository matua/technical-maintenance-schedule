package ug.payway.technicalmaintenanceschedule.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ug.payway.technicalmaintenanceschedule.config.Utility;
import ug.payway.technicalmaintenanceschedule.dto.TaskDto;
import ug.payway.technicalmaintenanceschedule.exception.NotFoundException;
import ug.payway.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import ug.payway.technicalmaintenanceschedule.exception.ValidationException;
import ug.payway.technicalmaintenanceschedule.model.Task;
import ug.payway.technicalmaintenanceschedule.service.TaskService;

@CrossOrigin
@RestController
@Slf4j
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping(Utility.ADMIN + Utility.TASKS)
    public ResponseEntity<Task> save(@RequestBody TaskDto taskDto) throws ValidationException, ResourceAlreadyExistsException {

        log.info("Handling task {} to create", taskDto);

        return ResponseEntity.ok(taskService.save(taskDto));
    }

    @PutMapping(Utility.ADMIN + Utility.TASKS)
    public ResponseEntity<Task> update(@RequestBody TaskDto taskDto) throws ValidationException,
            ResourceAlreadyExistsException, NotFoundException {

        log.info("Handling task to update with task {}", taskDto);

        Task updatedTask = taskService.update(taskDto);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping(Utility.ADMIN + Utility.TASKS + "/{taskDescription}")
    public ResponseEntity<Void> delete(@PathVariable String taskDescription) throws ValidationException,
            NotFoundException {

        log.info("Handling delete task with task description: {}", taskDescription);
        taskService.deleteByDescription(taskDescription);
        return ResponseEntity.accepted().build();
    }

    @GetMapping(Utility.TASKS + "/{taskId}")
    public ResponseEntity<Task> findById(@PathVariable Long taskId) throws ValidationException, NotFoundException {

        log.info("Handling task to find by taskId: {}", taskId);

        Task taskResponseEntity = taskService.findById(taskId);
        return ResponseEntity.ok().body(taskResponseEntity);
    }

    @GetMapping(Utility.TASKS + "/{page}/{pageSize}")
    public ResponseEntity<Page<Task>> findAll(
            @PathVariable Integer page,
            @PathVariable Integer pageSize) throws NotFoundException, IllegalAccessException, NoSuchFieldException {

        log.info("Handling find all tasks page: {} with size: {}", page, pageSize);

        Page<Task> tasksPageResponseBody = taskService.findAllByPage(page, pageSize);
        return ResponseEntity.ok().body(tasksPageResponseBody);
    }
}