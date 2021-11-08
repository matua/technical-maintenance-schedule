package com.matuageorge.technicalmaintenanceschedule.repository;

import com.matuageorge.technicalmaintenanceschedule.model.Role;
import com.matuageorge.technicalmaintenanceschedule.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    List<User> findByRole(Role role);
}
