package com.matuageorge.technicalmaintenanceschedule.service;

import com.matuageorge.technicalmaintenanceschedule.dto.TaskDto;
import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.Task;
import com.matuageorge.technicalmaintenanceschedule.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static com.matuageorge.technicalmaintenanceschedule.prototype.TaskDtoPrototype.aTaskDto;
import static com.matuageorge.technicalmaintenanceschedule.prototype.TaskDtoPrototype.bTaskDto;
import static com.matuageorge.technicalmaintenanceschedule.prototype.TaskPrototype.aTask;
import static com.matuageorge.technicalmaintenanceschedule.prototype.TaskPrototype.bTask;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {
    private TaskRepository taskRepository;
    private TaskService taskService;
    private ModelMapper modelMapper;


    @BeforeEach
    void setUp() {
        taskRepository = mock(TaskRepository.class);
        modelMapper = new ModelMapper();
        taskService = new TaskServiceImpl(taskRepository, modelMapper);
    }

    @Test
    void save() throws ValidationException, ResourceAlreadyExistsException {
        when(taskRepository.save(modelMapper.map(aTaskDto(), Task.class))).thenReturn(aTask());
        Task savedTask = taskService.save(aTaskDto());
        assertThat(savedTask).isNotNull();
        assertThat(savedTask.getId()).isEqualTo(modelMapper.map(aTask(), TaskDto.class).getId());
        assertThat(savedTask.getDescription()).isEqualTo(modelMapper.map(aTask(), TaskDto.class).getDescription());
        assertThat(savedTask.getFrequency()).isEqualTo(modelMapper.map(aTask(), TaskDto.class).getFrequency());
    }

    @Test
    void update() throws ValidationException, NotFoundException {
        when(taskRepository.findById(any())).thenReturn(Optional.ofNullable(aTask()));
        when(taskRepository.save(modelMapper.map(bTaskDto(), Task.class))).thenReturn(bTask());
        Task updatedTask = taskService.update(bTaskDto());
        assertThat(updatedTask).isNotNull();
        assertThat(updatedTask.getId()).isEqualTo(bTask().getId());

    }

    @Test
    void delete() throws ValidationException, NotFoundException {
        Long taskIdToDelete = 1L;
        when(taskRepository.findById(taskIdToDelete)).thenReturn(Optional.ofNullable(aTask()));
        taskService.delete(taskIdToDelete);
        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    void findAll() throws NotFoundException {
        int page = 0;
        int pageSize = 1;
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").descending());
        Page<Task> pageOfTasks = new PageImpl<>(List.of(aTask(), bTask()));
        when(taskRepository.findAll(pageable)).thenReturn(pageOfTasks);
        Page<Task> foundTasks = taskService.findAll(page, pageSize);
        assertThat(foundTasks).isNotNull();
        assertThat(foundTasks.getSize()).isEqualTo(2);
    }

    @Test
    void findById() throws ValidationException, NotFoundException {
        Long taskToFindId = 1L;
        when(taskRepository.findById(taskToFindId)).thenReturn(Optional.ofNullable(aTask()));
        Task foundTask = taskService.findById(taskToFindId);
        assertThat(foundTask).isNotNull();
    }

    @Test
    void findByIdThrowsNotFoundExceptionWhenProductIsNotFound() {
        when(taskRepository.findById(100L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> taskService.findById(100L));
    }
}