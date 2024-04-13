package com.example.technologiesieciowe.service.error.UserErrors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotBorrowedBookException extends RuntimeException {
    private UserNotBorrowedBookException(String message) {
        super(message);
    }

    public static ResponseStatusException create(String book_id) {
        UserNotBorrowedBookException exception = new UserNotBorrowedBookException(String.format("User hasn't borrowed book %s", book_id));
        return new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage(), exception);
    }
}
