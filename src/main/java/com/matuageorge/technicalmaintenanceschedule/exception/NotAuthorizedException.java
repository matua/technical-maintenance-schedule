package com.matuageorge.technicalmaintenanceschedule.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NotAuthorizedException extends Exception {
    private final String message;
}
