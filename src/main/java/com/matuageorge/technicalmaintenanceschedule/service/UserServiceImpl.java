package com.matuageorge.technicalmaintenanceschedule.service;

import com.matuageorge.technicalmaintenanceschedule.dto.UserDto;
import com.matuageorge.technicalmaintenanceschedule.exception.NotAuthorizedException;
import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.Role;
import com.matuageorge.technicalmaintenanceschedule.model.User;
import com.matuageorge.technicalmaintenanceschedule.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private static final String USER_NOT_FOUND = "User not found";
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto save(UserDto userDto) throws ValidationException, ResourceAlreadyExistsException {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("User with such email already exists");
        }
        User savedUser;
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        validateUserMainFields(modelMapper.map(userDto, User.class));
        try {
            savedUser = userRepository.save(modelMapper.map(userDto, User.class));
        } catch (IllegalArgumentException e) {
            throw new ValidationException("UserDto is null");
        }
        return modelMapper.map(savedUser, UserDto.class);
    }


    @Override
    public UserDto update(Long userId, UserDto userDto) {
        User userAfterUpdate = userRepository.save(modelMapper.map(userDto, User.class));
        return modelMapper.map(userAfterUpdate, UserDto.class);
    }

    @Override
    public void delete(Long userId) throws NotFoundException {
        try {
            userRepository.deleteById(userId);
        } catch (IllegalArgumentException e) {
            throw new NotFoundException(USER_NOT_FOUND);
        }
    }

    @Override
    public Page<User> findAll(Integer page, Integer pageSize) throws NotFoundException {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id"));

        Page<User> usersPage = userRepository.findAll(pageable);
        if (usersPage.hasContent()) {
            return usersPage;
        } else {
            throw new NotFoundException(USER_NOT_FOUND);
        }
    }

    @Override
    public UserDto findById(Long id) throws NotFoundException {
        return modelMapper.map(userRepository.findById(id).orElseThrow(
                () -> new NotFoundException(USER_NOT_FOUND)), UserDto.class);
    }

    @Override
    public UserDto findByEmail(String email) throws NotFoundException {
        return modelMapper.map(userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException(USER_NOT_FOUND)), UserDto.class);
    }

    @Override
    public UserDto findByEmailAndPassword(String email, String password) throws NotAuthorizedException, NotFoundException {
        UserDto userDtoFoundByEmail = findByEmail(email);

        if (userDtoFoundByEmail != null) {
            if (passwordEncoder.matches(password, userDtoFoundByEmail.getPassword())) {
                return userDtoFoundByEmail;
            } else {
                throw new NotAuthorizedException("Wrong credentials");
            }
        }
        return null;
    }

    @Override
    public User createNewUserIfDoesNotExist(String email, String firstName, String lastName, String password, Role role) {

        UserDto foundUser = null;

        try {
            foundUser = findByEmail(email);
        } catch (NotFoundException e) {
            log.info("User does not exist. Creating new user.");
        }
        User user;
        if (foundUser != null) {
            user = modelMapper.map(foundUser, User.class);
        } else {
            user = User.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .encryptedPassword(password)
                    .role(role)
                    .build();
        }
        return user;
    }

    @Override
    public void toggleUserStatusByUserId(Long userId) throws NotFoundException {

        UserDto userDtoToToggleStatus = findById(userId);

        userDtoToToggleStatus.setActive(!userDtoToToggleStatus.getActive());
        userRepository.save(modelMapper.map(userDtoToToggleStatus, User.class));
    }

    @Override
    public List<User> findAllByRole(Role role) {
        return userRepository.findByRole(role);
    }


    private void validateUserMainFields(User savedUser) throws ValidationException {
        if (savedUser.getFirstName().isBlank() || savedUser.getLastName().isEmpty()) {
            throw new ValidationException("First Name or Last Name cannot be empty");
        }
    }

    public List<User> getUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }
}
