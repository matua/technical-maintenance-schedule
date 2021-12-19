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
@RequestMapping(
    value = ADMIN,
    method = {RequestMethod.GET, RequestMethod.PUT})
public class UserController {

  private final UserService userService;

  @GetMapping(USERS + "/{page}/{pageSize}")
  public ResponseEntity<Page<User>> findAll(
      @PathVariable Integer page, @PathVariable Integer pageSize) throws NotFoundException {

    log.info("Handling find all users page: {} with size: {}", page, pageSize);

    Page<User> usersPageResponseBody = userService.findAll(page, pageSize);
    return ResponseEntity.ok().body(usersPageResponseBody);
  }

  @PutMapping(USERS + "/{userId}/toggleActive")
  public ResponseEntity<Void> toggleUserActiveByUserId(@PathVariable Long userId)
      throws ValidationException, NotFoundException, ResourceAlreadyExistsException {

    log.info("Toggling active status of user with id {}", userId);

    userService.toggleUserActiveByUserId(userId);
    return ResponseEntity.ok().build();
  }

  @PutMapping(USERS + "/{userId}/toggleOnDuty")
  public ResponseEntity<Void> toggleUserOnDutyByUserId(@PathVariable Long userId)
      throws NotFoundException {

    log.info("Toggling HAHA on duty status of user with id {}", userId);

    userService.toggleUserOnDutyByUserId(userId);
    return ResponseEntity.ok().build();
  }
}
