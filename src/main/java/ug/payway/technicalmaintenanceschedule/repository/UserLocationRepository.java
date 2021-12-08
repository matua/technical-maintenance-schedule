package ug.payway.technicalmaintenanceschedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ug.payway.technicalmaintenanceschedule.model.UserLocation;

@Repository
public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {}
