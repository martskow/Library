package com.example.technologiesieciowe.service.error.BookDetailsErrors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BookDetailsNotFoundException extends RuntimeException{
    private BookDetailsNotFoundException(String message) {
        super(message);
    }

    public static ResponseStatusException create(String detail) {
        BookDetailsNotFoundException exception = new BookDetailsNotFoundException(String.format("Book details by %s was not found", detail));
        return new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
    }
}
