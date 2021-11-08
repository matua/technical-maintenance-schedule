package com.matuageorge.technicalmaintenanceschedule.model;

import lombok.Getter;

@Getter
public enum TaskStatus {
    SCHEDULED("Scheduled"),
    GRABBED("Grabbed"),
    IN_PROGRESS("In Progress"),
    DONE("Done");

    private final String name;

    TaskStatus(String name) {
        this.name = name;
    }
}