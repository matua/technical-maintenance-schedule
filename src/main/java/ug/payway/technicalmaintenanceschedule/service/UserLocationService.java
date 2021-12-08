package ug.payway.technicalmaintenanceschedule.service;

import ug.payway.technicalmaintenanceschedule.model.User;
import ug.payway.technicalmaintenanceschedule.model.UserLocation;

public interface UserLocationService {
  UserLocation save(UserLocation userLocation);

  UserLocation findLastByUser(User user);
}
