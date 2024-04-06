package com.example.technologiesieciowe.service.error.UserErrors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserEmailExistsException extends RuntimeException{
    private UserEmailExistsException(String message) {
        super(message);
    }

    public static ResponseStatusException create(String email) {
        UserEmailExistsException exception = new UserEmailExistsException(String.format("A user with the specified email address: %s already exists.", email));
        return new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage(), exception);
    }
}
