package ug.payway.technicalmaintenanceschedule.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ug.payway.technicalmaintenanceschedule.model.User;
import ug.payway.technicalmaintenanceschedule.model.UserLocation;
import ug.payway.technicalmaintenanceschedule.repository.UserLocationRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserLocationServiceImpl implements UserLocationService {
  private final UserLocationRepository userLocationRepository;

  @Override
  public UserLocation save(UserLocation userLocation) {
    return userLocationRepository.save(userLocation);
  }

  @Override
  public UserLocation findLastByUser(User user) {
    return userLocationRepository.findTop1ByUserOrderByIdDesc(user).orElseThrow();
  }
}
