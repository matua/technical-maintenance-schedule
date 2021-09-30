package com.matuageorge.technicalmaintenanceschedule.service;

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

import static com.matuageorge.technicalmaintenanceschedule.prototype.TaskDtoPrototype.aTaskDto;
import static com.matuageorge.technicalmaintenanceschedule.prototype.TaskDtoPrototype.bTaskDto;
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
        aTaskDto().setId(1L);
        aTask().setId(1L);
        when(taskRepository.findById(aTaskDto().getId())).thenReturn(Optional.empty());
        when(taskRepository.save(modelMapper.map(aTaskDto(), Task.class))).thenReturn(aTask());
        Task savedTask = taskService.save(aTaskDto());
        assertThat(savedTask).isNotNull();
        verify(taskRepository).save(modelMapper.map(aTaskDto(), Task.class));
        assertThat(savedTask.getId()).isEqualTo(aTaskDto().getId());
        assertThat(savedTask.getDescription()).isEqualTo(aTaskDto().getDescription());
        assertThat(savedTask.getFrequency()).isEqualTo(aTaskDto().getFrequency());
    }

    @Test
    void saveThrowsResourceAlreadyExistsException() {
        aTaskDto().setId(1L);
        aTask().setId(1L);
        when(taskRepository.findById(aTaskDto().getId())).thenReturn(Optional.ofNullable(aTask()));
        assertThrows(ResourceAlreadyExistsException.class, () -> taskService.save(aTaskDto()));
        verify(taskRepository, never()).save(modelMapper.map(aTaskDto(), Task.class));
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
    void findByIdThrowsNotFoundExceptionWhenProductIsNotFound() {
        when(taskRepository.findById(100L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> taskService.findById(100L));
    }
}