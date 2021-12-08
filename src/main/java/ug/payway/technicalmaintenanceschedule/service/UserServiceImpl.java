package ug.payway.technicalmaintenanceschedule.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ug.payway.technicalmaintenanceschedule.dto.UserDto;
import ug.payway.technicalmaintenanceschedule.exception.NotAuthorizedException;
import ug.payway.technicalmaintenanceschedule.exception.NotFoundException;
import ug.payway.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import ug.payway.technicalmaintenanceschedule.exception.ValidationException;
import ug.payway.technicalmaintenanceschedule.model.Role;
import ug.payway.technicalmaintenanceschedule.model.User;
import ug.payway.technicalmaintenanceschedule.repository.UserRepository;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
  private static final String USER_NOT_FOUND = "User not found";
  private final UserRepository userRepository;
  private final ModelMapper modelMapper;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDto save(UserDto userDto) throws ValidationException, ResourceAlreadyExistsException {
    if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
      throw new ResourceAlreadyExistsException("User with such email already exists");
    }
    User savedUser;
    userDto.setEncryptedPassword(passwordEncoder.encode(userDto.getEncryptedPassword()));
    validateUserMainFields(modelMapper.map(userDto, User.class));
    try {
      savedUser = userRepository.save(modelMapper.map(userDto, User.class));
    } catch (IllegalArgumentException e) {
      throw new ValidationException("UserDto is null");
    }
    return modelMapper.map(savedUser, UserDto.class);
  }

  @Override
  public UserDto update(Long userId, UserDto userDto) throws NotFoundException {
    findById(userId);
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
    return modelMapper.map(
        userRepository.findById(id).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND)),
        UserDto.class);
  }

  @Override
  public User findByEmail(String email) throws NotFoundException {
    return userRepository
        .findByEmail(email)
        .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
  }

  @Override
  public User findByEmailAndPassword(String email, String password)
      throws NotAuthorizedException, NotFoundException {
    User userFoundByEmail = findByEmail(email);

    if (userFoundByEmail != null) {
      if (passwordEncoder.matches(password, userFoundByEmail.getEncryptedPassword())) {
        return userFoundByEmail;
      } else {
        throw new NotAuthorizedException("Wrong credentials");
      }
    }
    return null;
  }

  @Override
  public User createNewUserIfDoesNotExist(
      String email, String firstName, String lastName, String password, Role role) {
    try {
      User foundUser = findByEmail(email);
      log.error(
          "User with email: {}, firstName: {}, lastName: {} already exists.",
          email,
          firstName,
          lastName);
      return foundUser;
    } catch (NotFoundException e) {
      log.info("User does not exist. Creating new user.");
      return User.builder()
          .firstName(firstName)
          .lastName(lastName)
          .email(email)
          .encryptedPassword(password)
          .role(role)
          .build();
    }
  }

  @Override
  public void toggleUserStatusByUserId(Long userId) throws NotFoundException {

    UserDto user = findById(userId);

    user.setActive(!user.getActive());
    update(user.getId(), user);
  }

  @Override
  public List<User> findAllByRole(Role role) {
    return userRepository.findByRole(role);
  }

  @Override
  public List<User> findAllByRoleAndActiveAndOnDuty(Role role, Boolean active, Boolean onDuty) {
    return userRepository.findAllByRoleAndActiveAndOnDuty(role, active, onDuty);
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
