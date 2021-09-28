package com.matuageorge.technicalmaintenanceschedule.service;

import com.matuageorge.technicalmaintenanceschedule.dto.UserDto;
import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.Role;
import com.matuageorge.technicalmaintenanceschedule.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    UserDto save(UserDto userDto) throws ValidationException, NotFoundException, ResourceAlreadyExistsException;

    UserDto update(Long userId, UserDto userDto) throws ValidationException, NotFoundException;

    void delete(Long userId) throws ValidationException, NotFoundException;

    Page<User> findAll(Integer page, Integer pageSize
    ) throws NotFoundException;

    UserDto findById(Long id) throws ValidationException, NotFoundException;

    UserDto findByEmail(String email) throws ValidationException, NotFoundException;

    UserDto findByEmailAndPassword(String email, String password) throws ValidationException, NotFoundException;

    User createNewUserIfDoesNotExist(String email, String firstName, String lastName, String password);

    void toggleUserStatusByUserId(Long userId) throws ValidationException, NotFoundException;

    List<User> findAllByRole(Role role);
}
