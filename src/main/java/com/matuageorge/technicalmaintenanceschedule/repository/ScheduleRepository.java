package com.matuageorge.technicalmaintenanceschedule.repository;

import com.matuageorge.technicalmaintenanceschedule.model.Schedule;
import com.matuageorge.technicalmaintenanceschedule.model.Task;
import com.matuageorge.technicalmaintenanceschedule.model.TaskPriority;
import com.matuageorge.technicalmaintenanceschedule.model.Terminal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Optional<Schedule> findByTerminalAndTask(Terminal terminal, Task task);

    List<Schedule> findByEndExecutionDateTimeNotNull();

    @Modifying
    @Query(
            """
                    update Schedule s
                    set s.startExecutionDateTime = :startExecutionDateTime,
                    s.status = 'IN_PROGRESS' where s.id = :scheduleId
                    """
    )
    void startSchedule(Long scheduleId, LocalDateTime startExecutionDateTime);

    @Modifying
    @Query("update Schedule s set s.status = 'DONE', s.endExecutionDateTime = :endExecutionDateTime where s.id = :scheduleId")
    void completeSchedule(Long scheduleId, LocalDateTime endExecutionDateTime);


    Page<Schedule> findByEndExecutionDateTimeNull(Pageable pageable);

    Optional<Schedule> findByTerminalAndTaskAndEndExecutionDateTimeNull(Terminal terminal, Task task);

    Page<Schedule> findAllByOrderByTaskPriorityDesc(Pageable pageable);

    @Query("from Schedule as schedule0_" +
            " left join Task task1_ on " +
            "schedule0_.task.id=task1_.id" +
            " where schedule0_.endExecutionDateTime is null " +
            " order by task1_.priority desc")
    Page<Schedule> findAllSortedByTaskPriorityAndByEndExecutionDateTimeNull(Pageable pageable);

    List<Schedule> findAllByTaskPriority(TaskPriority priority);
}
