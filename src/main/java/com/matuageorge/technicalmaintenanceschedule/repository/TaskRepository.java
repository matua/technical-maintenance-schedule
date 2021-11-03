package com.matuageorge.technicalmaintenanceschedule.repository;

import com.matuageorge.technicalmaintenanceschedule.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByDescription(String description);

    Optional<Task> findByDescriptionOrMessageId(String description, Long messageId);

    @Query("from Task as t where t.description like %:descriptionSinceArgs%")
    Optional<Task> findByDescriptionArgsSince(String descriptionSinceArgs);
}
