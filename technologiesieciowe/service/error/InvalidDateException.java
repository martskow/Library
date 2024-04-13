package com.example.technologiesieciowe.service.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidDateException extends RuntimeException{
    private InvalidDateException(String message) {
        super(message);
    }

    public static ResponseStatusException create(String text) {
        InvalidDateException exception = new InvalidDateException(String.format("%s", text));
        return new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage(), exception);
    }
}
