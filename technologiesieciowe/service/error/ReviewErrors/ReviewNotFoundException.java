package com.example.technologiesieciowe.service.error.ReviewErrors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReviewNotFoundException extends RuntimeException {
    private ReviewNotFoundException(String message) {
        super(message);
    }

    public static ResponseStatusException create(String id) {
        ReviewNotFoundException exception = new ReviewNotFoundException(String.format("Review %s was not found", id));
        return new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
    }
}
