package ug.payway.technicalmaintenanceschedule.prototype;

import ug.payway.technicalmaintenanceschedule.model.Role;
import ug.payway.technicalmaintenanceschedule.model.User;

public class UserPrototype {
  public static User aUser() {
    return User.builder()
        .email("userA@email.com")
        .firstName("UserAFirstname")
        .lastName("UserAFirstname")
        .active(true)
        .role(Role.ADMINISTRATOR)
        .onDuty(true)
        .encryptedPassword("!@#")
        .build();
  }

  public static User bUser() {
    return User.builder()
        .email("userB@email.com")
        .firstName("UserBFirstname")
        .lastName("UserBFirstname")
        .active(false)
        .role(Role.TECHNICIAN)
        .onDuty(false)
        .encryptedPassword("@#$")
        .build();
  }
}
