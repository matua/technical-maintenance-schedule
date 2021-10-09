package com.matuageorge.technicalmaintenanceschedule.controller;

import com.matuageorge.technicalmaintenanceschedule.config.security.jwt.JwtProvider;
import com.matuageorge.technicalmaintenanceschedule.dto.UserAuthRequestDto;
import com.matuageorge.technicalmaintenanceschedule.exception.NotAuthorizedException;
import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.User;
import com.matuageorge.technicalmaintenanceschedule.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.matuageorge.technicalmaintenanceschedule.config.Utility.ADMIN;
import static com.matuageorge.technicalmaintenanceschedule.config.Utility.USERS;

@CrossOrigin
@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(ADMIN)
public class UserController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    @GetMapping(USERS + "/{page}/{pageSize}")
    public ResponseEntity<Page<User>> findAll(
            @PathVariable Integer page, @PathVariable Integer pageSize) throws NotFoundException {

        log.info("Handling find all users page: {} with size: {}", page, pageSize);

        Page<User> usersPageResponseBody = userService.findAll(page, pageSize);
        return ResponseEntity.ok().body(usersPageResponseBody);
    }

    @PutMapping(USERS + "/{userId}/toggle")
    public ResponseEntity<Void> toggleUserStatusByUserId(
            @PathVariable Long userId) throws ValidationException, NotFoundException {

        log.info("Toggling status of user with id {}", userId);

        userService.toggleUserStatusByUserId(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/auth")
    public ResponseEntity<String> authenticateUser(@RequestBody UserAuthRequestDto userAuthRequestDto) throws ValidationException, NotFoundException, NotAuthorizedException {

        log.info("Authorizing user with email: {} and password: {}",
                userAuthRequestDto.getEmail(),
                userAuthRequestDto.getPassword());

        User foundUser = userService.findByEmailAndPassword(
                userAuthRequestDto.getEmail(),
                userAuthRequestDto.getPassword());

        String token = jwtProvider.generateToken(
                foundUser.getEmail(),
                foundUser.getFirstName(),
                foundUser.getLastName(),
                foundUser.getActive().toString(),
                foundUser.getOnDuty().toString(),
                foundUser.getRole().toString(),
                foundUser.getId());

        return ResponseEntity.ok(token);
    }
}