package com.example.technologiesieciowe.service.error.ReviewErrors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(HttpStatus.CONFLICT)
public class RatingOffTheScaleException extends RuntimeException {
    private RatingOffTheScaleException(String message) {
        super(message);
    }

    public static ResponseStatusException create(String rating) {
        RatingOffTheScaleException exception = new RatingOffTheScaleException(String.format("The %s rating is not between 1 and 10", rating));
        return new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage(), exception);
    }
}
