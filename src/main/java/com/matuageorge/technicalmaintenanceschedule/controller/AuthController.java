package com.matuageorge.technicalmaintenanceschedule.controller;

import com.matuageorge.technicalmaintenanceschedule.config.security.jwt.JwtProvider;
import com.matuageorge.technicalmaintenanceschedule.dto.UserAuthRequestDto;
import com.matuageorge.technicalmaintenanceschedule.dto.UserAuthResponseDto;
import com.matuageorge.technicalmaintenanceschedule.exception.NotAuthorizedException;
import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import com.matuageorge.technicalmaintenanceschedule.model.User;
import com.matuageorge.technicalmaintenanceschedule.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    @PostMapping("/auth")
    public ResponseEntity<UserAuthResponseDto> authenticateUser(
            @RequestBody UserAuthRequestDto request) throws ValidationException, NotFoundException, NotAuthorizedException {

        log.info("Handling authorization request with email: {} and password: {}",
                request.getEmail(),
                request.getPassword());

        User foundUser = userService.findByEmailAndPassword(
                request.getEmail(),
                request.getPassword());

        String token = jwtProvider.generateToken(
                foundUser.getEmail(),
                foundUser.getFirstName(),
                foundUser.getLastName(),
                foundUser.getActive().toString(),
                foundUser.getOnDuty().toString(),
                foundUser.getRole().toString(),
                foundUser.getId());

        return ResponseEntity.ok().body(new UserAuthResponseDto(token));
    }
}