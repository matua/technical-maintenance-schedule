package ug.payway.technicalmaintenanceschedule.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ug.payway.technicalmaintenanceschedule.config.Utility;
import ug.payway.technicalmaintenanceschedule.exception.NotFoundException;
import ug.payway.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import ug.payway.technicalmaintenanceschedule.exception.ValidationException;
import ug.payway.technicalmaintenanceschedule.model.User;
import ug.payway.technicalmaintenanceschedule.model.UserLocation;
import ug.payway.technicalmaintenanceschedule.service.UserLocationService;
import ug.payway.technicalmaintenanceschedule.service.UserService;

@CrossOrigin
@RestController
@Slf4j
@AllArgsConstructor
public class UserLocationController {

  private final UserLocationService userLocationService;
  private final UserService userService;

  @PostMapping(Utility.USER_LOCATIONS + "/{latitude}/{longitude}")
  public ResponseEntity<UserLocation> save(
      @AuthenticationPrincipal UserDetails userDetails,
      @PathVariable String latitude,
      @PathVariable String longitude)
      throws ValidationException, ResourceAlreadyExistsException, NotFoundException {

    if (latitude.equalsIgnoreCase("undefined")) {
      return ResponseEntity.badRequest().build();
    }

    final User user = userService.findByEmail(userDetails.getUsername());

    UserLocation userLocation =
        UserLocation.builder()
            .user(user)
            .latitude(Double.valueOf(latitude))
            .longitude(Double.valueOf(longitude))
            .build();

    log.info(
        "User: {}, saving GPS locations in DB: {}, {}",
        userLocation.getUser().getEmail(),
        userLocation.getLatitude(),
        userLocation.getLongitude());

    return ResponseEntity.ok(userLocationService.save(userLocation));
  }
}
