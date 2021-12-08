package ug.payway.technicalmaintenanceschedule.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ug.payway.technicalmaintenanceschedule.model.Role;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class UserDto extends AbstractBaseDto {
  private Role role;
  private String firstName;
  private String lastName;
  private String email;
  private String encryptedPassword;
  private Boolean active;
  private Boolean onDuty;
  private Integer fieldHours;
}
