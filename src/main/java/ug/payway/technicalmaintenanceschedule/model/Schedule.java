package ug.payway.technicalmaintenanceschedule.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "schedules")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Schedule extends AbstractBaseEntity {
  @OneToOne(optional = false)
  @JoinColumn(name = "terminal_id", referencedColumnName = "id")
  private Terminal terminal;

  @OneToOne
  @JoinColumn(name = "task_id", referencedColumnName = "id")
  private Task task;

  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @Enumerated(EnumType.STRING)
  private TaskStatus status;

  @CreatedDate
  @Column(name = "date_time_created", updatable = false)
  private LocalDateTime dateTimeCreated;

  private LocalDateTime startExecutionDateTime;
  private LocalDateTime endExecutionDateTime;
  private LocalDateTime grabbedExecutionDateTime;
  private LocalDateTime releasedExecutionDateTime;
  private Long optimizationIndex;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    Schedule schedule = (Schedule) o;
    return Objects.equals(id, schedule.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), id);
  }
}
