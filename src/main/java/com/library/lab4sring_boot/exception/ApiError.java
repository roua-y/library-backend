package com.library.lab4sring_boot.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
public class ApiError {

    private String message;            // A human-readable message about the error
    private int status;                // HTTP status code (e.g., 400, 404)
    private LocalDateTime timestamp;
    private Map<String, String> errors; // When the error occurred
}
