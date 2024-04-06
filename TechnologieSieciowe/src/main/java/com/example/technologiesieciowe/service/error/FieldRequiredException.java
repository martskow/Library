package com.example.technologiesieciowe.service.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FieldRequiredException extends RuntimeException {
    private FieldRequiredException(String message) {
        super(message);
    }

    public static ResponseStatusException create(String fieldName) {
        FieldRequiredException exception = new FieldRequiredException(String.format("%s is required and cannot be null.", fieldName));
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);
    }
}
