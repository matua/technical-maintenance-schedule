package ug.payway.technicalmaintenanceschedule.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ug.payway.technicalmaintenanceschedule.model.Schedule;
import ug.payway.technicalmaintenanceschedule.model.Task;
import ug.payway.technicalmaintenanceschedule.model.TaskPriority;
import ug.payway.technicalmaintenanceschedule.model.Terminal;

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

    @Query("from Schedule as schedule0_" +
            " left join Task task1_ on " +
            "schedule0_.task.id=task1_.id" +
            " where schedule0_.endExecutionDateTime is null" +
            " and schedule0_.user.email = :email " +
            " order by task1_.priority desc")
    Page<Schedule> findAllSortedByTaskPriorityAndByEndExecutionDateTimeNullAndByEmail(Pageable pageable, String email);

    @Modifying
    @Query(
            """
                    update Schedule s
                    set s.grabbedExecutionDateTime = :grabbedExecutionDateTime,
                    s.releasedExecutionDateTime = null,
                    s.status = 'GRABBED' where s.id = :scheduleId
                    """
    )
    void grabSchedule(Long scheduleId, LocalDateTime grabbedExecutionDateTime);

    @Modifying
    @Query(
            """
                    update Schedule s
                    set s.releasedExecutionDateTime = :releasedExecutionDateTime,
                    s.grabbedExecutionDateTime = null,
                    s.status = 'SCHEDULED' where s.id = :scheduleId
                    """
    )
    void releaseSchedule(Long scheduleId, LocalDateTime releasedExecutionDateTime);

    @Modifying
    @Query(
            """
                    update Schedule s
                    set s.user.id = :userId
                    where s.id = :scheduleId
                     """
    )
    void setUser(Long scheduleId, Long userId);

    @Modifying
    @Query(
            """
                    update Schedule s
                    set s.optimizationIndex = :optimizationIndex
                    where s.id = :scheduleId
                     """
    )
    void setOptimizationIndex(Long scheduleId, Long optimizationIndex);

    @Query("from Schedule as schedule0_" +
            " left join Task task1_ on " +
            "schedule0_.task.id=task1_.id" +
            " where schedule0_.endExecutionDateTime is null" +
            " and schedule0_.user.email = :email ")
    Page<Schedule> findAllSortedByOptimizationIndexAndByEndExecutionDateTimeNullAndByUserEmail(
            Pageable pageable, String email);

    List<Schedule> findAllByTerminalAndTask(Terminal terminal, Task task);

    List<Schedule> findAllByTerminalAndTaskAndEndExecutionDateTimeNull(Terminal terminal, Task task);

    List<Schedule> findAllByTaskPriorityAndEndExecutionDateTimeNull(TaskPriority taskPriority);
}
