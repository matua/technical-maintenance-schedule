package com.matuageorge.technicalmaintenanceschedule.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
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


}