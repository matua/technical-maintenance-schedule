package ug.payway.technicalmaintenanceschedule.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResourceAlreadyExistsException extends Exception {
    private final String message;
}
