package com.example.technologiesieciowe.service.error.UserErrors;

import com.example.technologiesieciowe.service.error.ReviewErrors.ReviewNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotFoundException extends RuntimeException {
    private UserNotFoundException(String message) {
        super(message);
    }

    public static ResponseStatusException create(String id) {
        UserNotFoundException exception = new UserNotFoundException(String.format("User %s was not found", id));
        return new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
    }
}