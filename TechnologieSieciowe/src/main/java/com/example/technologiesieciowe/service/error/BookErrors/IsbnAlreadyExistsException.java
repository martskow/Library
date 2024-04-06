package com.example.technologiesieciowe.service.error.BookErrors;

import com.example.technologiesieciowe.service.error.UserErrors.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class IsbnAlreadyExistsException extends RuntimeException {
    private IsbnAlreadyExistsException(String message) {
        super(message);
    }

    public static ResponseStatusException create(String isbn) {
        IsbnAlreadyExistsException exception = new IsbnAlreadyExistsException(String.format("Book with ISBN number: %s already exists.", isbn));
        return new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage(), exception);
    }
}
