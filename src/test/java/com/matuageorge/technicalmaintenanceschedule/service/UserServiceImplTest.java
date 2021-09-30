package com.matuageorge.technicalmaintenanceschedule.service;

import com.matuageorge.technicalmaintenanceschedule.dto.UserDto;
import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.User;
import com.matuageorge.technicalmaintenanceschedule.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static com.matuageorge.technicalmaintenanceschedule.prototype.UserPrototype.aUser;
import static com.matuageorge.technicalmaintenanceschedule.prototype.UserPrototype.bUser;
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
        aUser().setId(1L);
        when(userRepository.findByEmail(aUser().getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(aUser());
        UserDto createdUser = userService.save(modelMapper.map(aUser(), UserDto.class));
        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getFirstName()).isEqualTo(modelMapper.map(aUser(), UserDto.class).getFirstName());
    }

    @Test
    void saveThrowsResourceAlreadyExistsException() {
        aUser().setId(1L);
        when(userRepository.findByEmail(aUser().getEmail())).thenReturn(Optional.ofNullable(aUser()));
        assertThrows(ResourceAlreadyExistsException.class,
                () -> userService.save(modelMapper.map(aUser(), UserDto.class)));
        verify(userRepository, never()).save(aUser());
    }

    @Test
    void saveThrowsValidationExceptionWhenUserFirstNameIsBlank() {
        aUser().setFirstName("");
        UserDto userDtoWithBlankFirstName = modelMapper.map(aUser(), UserDto.class);
        assertThrows(IllegalArgumentException.class, () -> userService.save(userDtoWithBlankFirstName));
    }

    @Test
    void update() throws ValidationException, NotFoundException {
        when(userRepository.save(any())).thenReturn(bUser());
        when(userRepository.findById(1000L)).thenReturn(Optional.ofNullable(aUser()));
        UserDto updatedUserDto = userService.update(1000L, modelMapper.map(bUser(), UserDto.class));
        assertThat(updatedUserDto).isNotNull();
        assertThat(updatedUserDto.getFirstName()).isEqualTo(modelMapper.map(bUser(), UserDto.class).getFirstName());
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
        Page<User> pageOfUsers = new PageImpl<>(List.of(aUser(), bUser()));
        when(userRepository.findAll(pageable)).thenReturn(pageOfUsers);
        Page<User> foundUsers = userService.findAll(0, 2);
        assertThat(foundUsers).isNotNull();
        assertThat(foundUsers.getSize()).isEqualTo(2);
    }

    @Test
    void findById() throws ValidationException, NotFoundException {
        when(userRepository.findById(1000L)).thenReturn(Optional.of(aUser()));
        UserDto foundUser = userService.findById(1000L);
        assertThat(foundUser).isNotNull();
    }

    @Test
    void findByIdThrowsNotFoundExceptionWhenUserIsNotFound() {
        when(userRepository.findById(100L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userService.findById(100L));
    }
}