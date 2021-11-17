package ug.payway.technicalmaintenanceschedule.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ug.payway.technicalmaintenanceschedule.dto.UserDto;
import ug.payway.technicalmaintenanceschedule.exception.NotFoundException;
import ug.payway.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import ug.payway.technicalmaintenanceschedule.exception.ValidationException;
import ug.payway.technicalmaintenanceschedule.model.User;
import ug.payway.technicalmaintenanceschedule.prototype.UserPrototype;
import ug.payway.technicalmaintenanceschedule.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    private UserService userService;
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        modelMapper = new ModelMapper();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserServiceImpl(userRepository, modelMapper, passwordEncoder);
    }

    @Test
    void save() throws ValidationException, NotFoundException, ResourceAlreadyExistsException {
        UserPrototype.aUser().setId(1L);
        when(userRepository.findByEmail(UserPrototype.aUser().getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(UserPrototype.aUser());
        UserDto createdUser = userService.save(modelMapper.map(UserPrototype.aUser(), UserDto.class));
        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getFirstName()).isEqualTo(modelMapper.map(UserPrototype.aUser(), UserDto.class).getFirstName());
    }

    @Test
    void saveThrowsResourceAlreadyExistsException() {
        UserPrototype.aUser().setId(1L);
        when(userRepository.findByEmail(UserPrototype.aUser().getEmail())).thenReturn(Optional.ofNullable(UserPrototype.aUser()));
        assertThrows(ResourceAlreadyExistsException.class,
                () -> userService.save(modelMapper.map(UserPrototype.aUser(), UserDto.class)));
        verify(userRepository, never()).save(UserPrototype.aUser());
    }

    @Test
    void saveThrowsValidationExceptionWhenUserFirstNameIsBlank() {
        UserPrototype.aUser().setFirstName("");
        UserDto userDtoWithBlankFirstName = modelMapper.map(UserPrototype.aUser(), UserDto.class);
        assertThrows(IllegalArgumentException.class, () -> userService.save(userDtoWithBlankFirstName));
    }

    @Test
    void update() throws ValidationException, NotFoundException {
        when(userRepository.save(any())).thenReturn(UserPrototype.bUser());
        when(userRepository.findById(1000L)).thenReturn(Optional.ofNullable(UserPrototype.aUser()));
        UserDto updatedUserDto = userService.update(1000L, modelMapper.map(UserPrototype.bUser(), UserDto.class));
        assertThat(updatedUserDto).isNotNull();
        assertThat(updatedUserDto.getFirstName()).isEqualTo(modelMapper.map(UserPrototype.bUser(), UserDto.class).getFirstName());
    }

    @Test
    void delete() throws ValidationException, NotFoundException {
        Long userId = 100L;
        userService.delete(userId);
        verify(userRepository, times(1)).deleteById(100L);
    }

    @Test
    void findAll() throws NotFoundException {
        Pageable pageable = PageRequest.of(0, 2, Sort.by("id"));
        Page<User> pageOfUsers = new PageImpl<>(List.of(UserPrototype.aUser(), UserPrototype.bUser()));
        when(userRepository.findAll(pageable)).thenReturn(pageOfUsers);
        Page<User> foundUsers = userService.findAll(0, 2);
        assertThat(foundUsers).isNotNull();
        assertThat(foundUsers.getSize()).isEqualTo(2);
    }

    @Test
    void findById() throws ValidationException, NotFoundException {
        when(userRepository.findById(1000L)).thenReturn(Optional.of(UserPrototype.aUser()));
        UserDto foundUser = userService.findById(1000L);
        assertThat(foundUser).isNotNull();
    }

    @Test
    void findByIdThrowsNotFoundExceptionWhenUserIsNotFound() {
        when(userRepository.findById(100L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userService.findById(100L));
    }
}