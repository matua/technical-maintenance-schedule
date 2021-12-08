package ug.payway.technicalmaintenanceschedule.service;

import org.springframework.data.domain.Page;
import ug.payway.technicalmaintenanceschedule.dto.TaskDto;
import ug.payway.technicalmaintenanceschedule.exception.NotFoundException;
import ug.payway.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import ug.payway.technicalmaintenanceschedule.exception.ValidationException;
import ug.payway.technicalmaintenanceschedule.model.Task;
import ug.payway.technicalmaintenanceschedule.model.TaskPriority;

import java.util.List;
import java.util.Optional;

public interface TaskService {
  Task save(TaskDto taskDto) throws ValidationException, ResourceAlreadyExistsException;

  Task update(TaskDto taskDto) throws ValidationException, NotFoundException;

  void delete(Long taskId) throws ValidationException, NotFoundException;

  Page<Task> findAllByPage(Integer page, Integer pageSize) throws NotFoundException;

  List<Task> findAll();

  Task findById(Long id) throws ValidationException, NotFoundException;

  Optional<Task> findByDescription(String taskDescription);

  void deleteByDescription(String taskDescription) throws NotFoundException;

  List<Task> findAllCommon(TaskPriority priority);
}
