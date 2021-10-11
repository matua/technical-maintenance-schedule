package com.matuageorge.technicalmaintenanceschedule.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
@Table(name = "schedules")
@Entity
public class Schedule extends AbstractBaseEntity {
    @OneToOne(optional = false)
    @JoinColumn(name = "terminal", referencedColumnName = "id")
    private Terminal terminal;
    @OneToOne(optional = false)
    @JoinColumn(name = "task", referencedColumnName = "id")
    private Task task;
    @Enumerated
    private TaskStatus status;
    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private LocalDateTime startExecutionDateTime;
    private LocalDateTime endExecutionTestTime;
}