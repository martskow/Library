package com.example.technologiesieciowe.service.error.QueueErrors;

import com.example.technologiesieciowe.service.error.ReviewErrors.ReviewNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class QueueNotFoundException extends RuntimeException {
    private QueueNotFoundException(String message) {
        super(message);
    }

    public static ResponseStatusException create(String id) {
        QueueNotFoundException exception = new QueueNotFoundException(String.format("A place %s in the queue  was not found", id));
        return new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
    }
}