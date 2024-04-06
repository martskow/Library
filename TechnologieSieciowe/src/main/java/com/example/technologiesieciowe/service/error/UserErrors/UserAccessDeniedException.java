package com.example.technologiesieciowe.service.error.UserErrors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserAccessDeniedException extends RuntimeException{
    private UserAccessDeniedException(String message) {
        super(message);
    }

    public static ResponseStatusException create(String text) {
        UserAccessDeniedException exception = new UserAccessDeniedException(text);
        return new ResponseStatusException(HttpStatus.FORBIDDEN, exception.getMessage(), exception);
    }
}
