package ug.payway.technicalmaintenanceschedule.controller.exception;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CustomExceptionMessage {
    private int statusCode;
    private LocalDateTime timestamp;
    private String message;
    private String description;
}
