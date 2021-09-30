package com.matuageorge.technicalmaintenanceschedule.service;

import com.matuageorge.technicalmaintenanceschedule.dto.TaskDto;
import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.Task;
import com.matuageorge.technicalmaintenanceschedule.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static com.matuageorge.technicalmaintenanceschedule.prototype.TaskPrototype.aTask;
import static com.matuageorge.technicalmaintenanceschedule.prototype.TaskPrototype.bTask;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {
    @Mock
    private TaskRepository taskRepository;
    private TaskService taskService;
    private ModelMapper modelMapper;


    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        taskService = new TaskServiceImpl(taskRepository, modelMapper);
    }

    @Test
    void save() throws ValidationException, ResourceAlreadyExistsException {
        aTask().setId(1L);
        when(taskRepository.findById(aTask().getId())).thenReturn(Optional.empty());
        when(taskRepository.save(aTask())).thenReturn(aTask());
        Task savedTask = taskService.save(modelMapper.map(aTask(), TaskDto.class));
        assertThat(savedTask).isNotNull();
        assertThat(savedTask).isEqualTo(aTask());
        verify(taskRepository).save(aTask());
    }

    @Test
    void saveThrowsResourceAlreadyExistsException() {
        aTask().setId(1L);
        when(taskRepository.findById(aTask().getId())).thenReturn(Optional.ofNullable(aTask()));
        assertThrows(ResourceAlreadyExistsException.class, () -> taskService.save(modelMapper.map(aTask(), TaskDto.class)));
        verify(taskRepository, never()).save(aTask());
    }

    @Test
    void update() throws ValidationException, NotFoundException {
        when(taskRepository.findById(any())).thenReturn(Optional.ofNullable(aTask()));
        when(taskRepository.save(bTask())).thenReturn(bTask());
        Task updatedTask = taskService.update(modelMapper.map(bTask(), TaskDto.class));
        assertThat(updatedTask).isNotNull();
        assertThat(updatedTask).isEqualTo(bTask());
    }

    @Test
    void delete() throws ValidationException, NotFoundException {
        Long taskIdToDelete = 1L;
        when(taskRepository.findById(taskIdToDelete)).thenReturn(Optional.ofNullable(aTask()));
        taskService.delete(taskIdToDelete);
        verify(taskRepository).deleteById(1L);
    }

    @Test
    void findAll() throws NotFoundException {
        int page = 0;
        int pageSize = 1;
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").descending());
        List<Task> tasks = List.of(aTask(), bTask());
        Page<Task> pageOfTasks = new PageImpl<>(tasks);
        when(taskRepository.findAll(pageable)).thenReturn(pageOfTasks);
        Page<Task> foundTasks = taskService.findAll(page, pageSize);
        assertThat(foundTasks).isNotNull();
        assertThat(foundTasks.getSize()).isEqualTo(tasks.size());
        verify(taskRepository).findAll(pageable);
    }

    @Test
    void findById() throws ValidationException, NotFoundException {
        Long taskToFindId = 1L;
        when(taskRepository.findById(taskToFindId)).thenReturn(Optional.ofNullable(aTask()));
        Task foundTask = taskService.findById(taskToFindId);
        assertThat(foundTask).isNotNull();
        assertEquals("General service A", aTask().getDescription());
        assertEquals(30, aTask().getFrequency());
    }

    @Test
    void findByIdThrowsNotFoundExceptionWhenTaskIsNotFound() {
        when(taskRepository.findById(100L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> taskService.findById(100L));
    }
}