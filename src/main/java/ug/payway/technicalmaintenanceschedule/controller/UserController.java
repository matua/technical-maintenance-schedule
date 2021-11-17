package ug.payway.technicalmaintenanceschedule.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ug.payway.technicalmaintenanceschedule.exception.NotFoundException;
import ug.payway.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import ug.payway.technicalmaintenanceschedule.exception.ValidationException;
import ug.payway.technicalmaintenanceschedule.model.User;
import ug.payway.technicalmaintenanceschedule.service.UserService;

import static ug.payway.technicalmaintenanceschedule.config.Utility.ADMIN;
import static ug.payway.technicalmaintenanceschedule.config.Utility.USERS;

@CrossOrigin
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(ADMIN)
public class UserController {

    private final UserService userService;

    @GetMapping(USERS + "/{page}/{pageSize}")
    public ResponseEntity<Page<User>> findAll(
            @PathVariable Integer page, @PathVariable Integer pageSize) throws NotFoundException {

        log.info("Handling find all users page: {} with size: {}", page, pageSize);

        Page<User> usersPageResponseBody = userService.findAll(page, pageSize);
        return ResponseEntity.ok().body(usersPageResponseBody);
    }

    @PutMapping(USERS + "/{userId}/toggle")
    public ResponseEntity<Void> toggleUserStatusByUserId(
            @PathVariable Long userId) throws ValidationException, NotFoundException, ResourceAlreadyExistsException {

        log.info("Toggling status of user with id {}", userId);

        userService.toggleUserStatusByUserId(userId);
        return ResponseEntity.ok().build();
    }
}