package ug.payway.technicalmaintenanceschedule.service;

import org.springframework.data.domain.Page;
import ug.payway.technicalmaintenanceschedule.dto.UserDto;
import ug.payway.technicalmaintenanceschedule.exception.NotAuthorizedException;
import ug.payway.technicalmaintenanceschedule.exception.NotFoundException;
import ug.payway.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import ug.payway.technicalmaintenanceschedule.exception.ValidationException;
import ug.payway.technicalmaintenanceschedule.model.Role;
import ug.payway.technicalmaintenanceschedule.model.User;

import java.util.List;

public interface UserService {
    UserDto save(UserDto userDto) throws ValidationException, NotFoundException, ResourceAlreadyExistsException;

    UserDto update(Long userId, UserDto userDto) throws ValidationException, NotFoundException;

    void delete(Long userId) throws ValidationException, NotFoundException;

    Page<User> findAll(Integer page, Integer pageSize
    ) throws NotFoundException;

    UserDto findById(Long id) throws ValidationException, NotFoundException;

    User findByEmail(String email) throws ValidationException, NotFoundException;

    User findByEmailAndPassword(String email, String password) throws ValidationException, NotFoundException, NotAuthorizedException;

    User createNewUserIfDoesNotExist(String email, String firstName, String lastName, String password, Role role);

    void toggleUserStatusByUserId(Long userId) throws ValidationException, NotFoundException, ResourceAlreadyExistsException;

    List<User> findAllByRole(Role role);

    List<User> findAllByRoleAndActiveAndOnDuty(Role role, Boolean active, Boolean onDuty);
}