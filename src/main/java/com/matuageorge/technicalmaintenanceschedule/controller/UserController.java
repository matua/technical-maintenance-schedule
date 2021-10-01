package com.matuageorge.technicalmaintenanceschedule.controller;

import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.User;
import com.matuageorge.technicalmaintenanceschedule.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/admin")
public class UserController {

    private final UserService userService;

    @GetMapping("/users/{page}/{pageSize}")
    public ResponseEntity<Page<User>> findAll(
            @PathVariable Integer page, @PathVariable Integer pageSize) throws NotFoundException {

        log.info("Handling find all users page: {} with size: {}", page, pageSize);

        Page<User> usersPageResponseBody = userService.findAll(page, pageSize);
        return ResponseEntity.ok().body(usersPageResponseBody);
    }

    @PutMapping("/users/{userId}/toggle")
    public ResponseEntity<Void> toggleUserStatusByUserId(
            @PathVariable Long userId) throws ValidationException, NotFoundException {

        log.info("Toggling status of user with id {}", userId);

        userService.toggleUserStatusByUserId(userId);
        return ResponseEntity.ok().build();
    }
}