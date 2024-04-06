package com.example.technologiesieciowe.service.error.BookErrors;

import com.example.technologiesieciowe.service.error.ReviewErrors.ReviewNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BookNotFoundException extends RuntimeException{
    private BookNotFoundException(String message) {
        super(message);
    }

    public static ResponseStatusException create(String id) {
        BookNotFoundException exception = new BookNotFoundException(String.format("Book %s was not found", id));
        return new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
    }
}
