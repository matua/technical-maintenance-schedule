// package ug.payway.technicalmaintenanceschedule.service;
//
// import org.junit.jupiter.api.Assertions;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.modelmapper.ModelMapper;
// import org.springframework.data.domain.*;
// import ug.payway.technicalmaintenanceschedule.dto.TaskDto;
// import ug.payway.technicalmaintenanceschedule.exception.NotFoundException;
// import ug.payway.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
// import ug.payway.technicalmaintenanceschedule.exception.ValidationException;
// import ug.payway.technicalmaintenanceschedule.model.Task;
// import ug.payway.technicalmaintenanceschedule.prototype.TaskPrototype;
// import ug.payway.technicalmaintenanceschedule.repository.TaskRepository;
//
// import java.util.List;
// import java.util.Optional;
//
// import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.*;
//
// @ExtendWith(MockitoExtension.class)
// class TaskServiceImplTest {
//    @Mock
//    private TaskRepository taskRepository;
//    private TaskService taskService;
//    private ModelMapper modelMapper;
//
//
//    @BeforeEach
//    void setUp() {
//        modelMapper = new ModelMapper();
//        taskService = new TaskServiceImpl(taskRepository, modelMapper,);
//    }
//
//    @Test
//    void save() throws ValidationException, ResourceAlreadyExistsException {
//        TaskPrototype.aTask().setId(1L);
//        when(taskRepository.findById(TaskPrototype.aTask().getId())).thenReturn(Optional.empty());
//        when(taskRepository.save(TaskPrototype.aTask())).thenReturn(TaskPrototype.aTask());
//        Task savedTask = taskService.save(modelMapper.map(TaskPrototype.aTask(), TaskDto.class));
//        assertThat(savedTask).isNotNull();
//        assertThat(savedTask).isEqualTo(TaskPrototype.aTask());
//        verify(taskRepository).save(TaskPrototype.aTask());
//    }
//
//    @Test
//    void saveThrowsResourceAlreadyExistsException() {
//        TaskPrototype.aTask().setId(1L);
//
// when(taskRepository.findById(TaskPrototype.aTask().getId())).thenReturn(Optional.ofNullable(TaskPrototype.aTask()));
//        assertThrows(ResourceAlreadyExistsException.class, () ->
// taskService.save(modelMapper.map(TaskPrototype.aTask(), TaskDto.class)));
//        verify(taskRepository, never()).save(TaskPrototype.aTask());
//    }
//
//    @Test
//    void update() throws ValidationException, NotFoundException {
//
// when(taskRepository.findById(any())).thenReturn(Optional.ofNullable(TaskPrototype.aTask()));
//        when(taskRepository.save(TaskPrototype.bTask())).thenReturn(TaskPrototype.bTask());
//        Task updatedTask = taskService.update(modelMapper.map(TaskPrototype.bTask(),
// TaskDto.class));
//        assertThat(updatedTask).isNotNull();
//        assertThat(updatedTask).isEqualTo(TaskPrototype.bTask());
//    }
//
//    @Test
//    void delete() throws ValidationException, NotFoundException {
//        Long taskIdToDelete = 1L;
//
// when(taskRepository.findById(taskIdToDelete)).thenReturn(Optional.ofNullable(TaskPrototype.aTask()));
//        taskService.delete(taskIdToDelete);
//        verify(taskRepository).deleteById(1L);
//    }
//
//    @Test
//    void findAll() throws NotFoundException {
//        int page = 0;
//        int pageSize = 1;
//        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").descending());
//        List<Task> tasks = List.of(TaskPrototype.aTask(), TaskPrototype.bTask());
//        Page<Task> pageOfTasks = new PageImpl<>(tasks);
//        when(taskRepository.findAll(pageable)).thenReturn(pageOfTasks);
//        Page<Task> foundTasks = taskService.findAllByPage(page, pageSize);
//        assertThat(foundTasks).isNotNull();
//        assertThat(foundTasks.getSize()).isEqualTo(tasks.size());
//        verify(taskRepository).findAll(pageable);
//    }
//
//    @Test
//    void findById() throws ValidationException, NotFoundException {
//        Long taskToFindId = 1L;
//
// when(taskRepository.findById(taskToFindId)).thenReturn(Optional.ofNullable(TaskPrototype.aTask()));
//        Task foundTask = taskService.findById(taskToFindId);
//        assertThat(foundTask).isNotNull();
//        Assertions.assertEquals("General service A", TaskPrototype.aTask().getDescription());
//        Assertions.assertEquals(30, TaskPrototype.aTask().getFrequency());
//    }
//
//    @Test
//    void findByIdThrowsNotFoundExceptionWhenTaskIsNotFound() {
//        when(taskRepository.findById(100L)).thenReturn(Optional.empty());
//        assertThrows(NotFoundException.class, () -> taskService.findById(100L));
//    }
// }
