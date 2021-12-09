package ug.payway.technicalmaintenanceschedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ug.payway.technicalmaintenanceschedule.model.Task;
import ug.payway.technicalmaintenanceschedule.model.TaskPriority;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
  Optional<Task> findByDescription(String description);

  @Query("from Task as t where t.description like %:descriptionSinceArgs%")
  Optional<Task> findByDescriptionArgsSince(String descriptionSinceArgs);

  List<Task> findByPriorityEquals(TaskPriority priority);

  //  @Query("select ")
  Task findFirstByOrderByFrequencyDesc();
}
