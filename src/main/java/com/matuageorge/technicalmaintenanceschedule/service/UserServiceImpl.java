package com.matuageorge.technicalmaintenanceschedule.service;

import com.matuageorge.technicalmaintenanceschedule.dto.UserDto;
import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.Role;
import com.matuageorge.technicalmaintenanceschedule.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

public class UserServiceImpl implements UserService {
    @Override
    public UserDto save(UserDto userDto) throws ValidationException, NotFoundException, ResourceAlreadyExistsException {
        return null;
    }

    @Override
    public UserDto update(Long userId, UserDto userDto) throws ValidationException, NotFoundException {
        return null;
    }

    @Override
    public void delete(Long userId) throws ValidationException, NotFoundException {

    }

    @Override
    public Page<User> findAll(Integer page, Integer pageSize) throws NotFoundException {
        return null;
    }

    @Override
    public UserDto findById(Long id) throws ValidationException, NotFoundException {
        return null;
    }

    @Override
    public UserDto findByEmail(String email) throws ValidationException, NotFoundException {
        return null;
    }

    @Override
    public UserDto findByEmailAndPassword(String email, String password) throws ValidationException, NotFoundException {
        return null;
    }

    @Override
    public User createNewUserIfDoesNotExist(String email, String firstName, String lastName, String password) {
        return null;
    }

    @Override
    public void toggleUserStatusByUserId(Long userId) throws ValidationException, NotFoundException {

    }

    @Override
    public List<User> findAllByRole(Role role) {
        return null;
    }
}
