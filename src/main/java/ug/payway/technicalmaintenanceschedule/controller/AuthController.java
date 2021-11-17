package ug.payway.technicalmaintenanceschedule.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ug.payway.technicalmaintenanceschedule.config.security.jwt.JwtProvider;
import ug.payway.technicalmaintenanceschedule.dto.UserAuthRequestDto;
import ug.payway.technicalmaintenanceschedule.dto.UserAuthResponseDto;
import ug.payway.technicalmaintenanceschedule.exception.NotAuthorizedException;
import ug.payway.technicalmaintenanceschedule.exception.NotFoundException;
import ug.payway.technicalmaintenanceschedule.exception.ValidationException;
import ug.payway.technicalmaintenanceschedule.model.User;
import ug.payway.technicalmaintenanceschedule.service.UserService;

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