package com.example.technologiesieciowe.service.error.UserErrors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserAlreadyExistsException extends RuntimeException {
    private UserAlreadyExistsException(String message) {
        super(message);
    }

    public static ResponseStatusException create(String userName) {
        UserAlreadyExistsException exception = new UserAlreadyExistsException(String.format("User with user name: %s already exists.", userName));
        return new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage(), exception);
    }
}
