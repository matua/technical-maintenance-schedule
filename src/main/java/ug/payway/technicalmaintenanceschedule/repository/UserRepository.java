package ug.payway.technicalmaintenanceschedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ug.payway.technicalmaintenanceschedule.model.Role;
import ug.payway.technicalmaintenanceschedule.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);

  List<User> findByRole(Role role);

  List<User> findAllByRoleAndActiveAndOnDuty(Role technician, boolean active, boolean onDuty);
}
