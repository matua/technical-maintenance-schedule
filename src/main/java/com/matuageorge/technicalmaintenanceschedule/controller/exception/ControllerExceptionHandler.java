package com.matuageorge.technicalmaintenanceschedule.controller.exception;

import com.matuageorge.technicalmaintenanceschedule.exception.NotAuthorizedException;
import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.exception.ResourceAlreadyExistsException;
import com.matuageorge.technicalmaintenanceschedule.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<CustomExceptionMessage> validationException(ValidationException ex, WebRequest request) {
        CustomExceptionMessage message = new CustomExceptionMessage(
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<CustomExceptionMessage> alreadyExistsException(ResourceAlreadyExistsException ex, WebRequest request) {
        CustomExceptionMessage message = new CustomExceptionMessage(
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CustomExceptionMessage> alreadyExistsException(NotFoundException ex, WebRequest request) {
        CustomExceptionMessage message = new CustomExceptionMessage(
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<CustomExceptionMessage> notAuthorizedException(NotAuthorizedException ex, WebRequest request) {
        CustomExceptionMessage message = new CustomExceptionMessage(
                HttpStatus.FORBIDDEN.value(),
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }
}
