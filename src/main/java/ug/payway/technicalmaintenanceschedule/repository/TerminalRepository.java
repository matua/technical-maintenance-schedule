package ug.payway.technicalmaintenanceschedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ug.payway.technicalmaintenanceschedule.model.Terminal;

import java.util.Optional;

@Repository
public interface TerminalRepository extends JpaRepository<Terminal, Long> {
  Optional<Terminal> findByName(String name);
}
