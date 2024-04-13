package com.example.technologiesieciowe.service.error.LoanArchiveErrors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LoanArchiveNotFoundException extends RuntimeException {
    private LoanArchiveNotFoundException(String message) {
        super(message);
    }

    public static ResponseStatusException create(String id) {
        LoanArchiveNotFoundException exception = new LoanArchiveNotFoundException(String.format("Archival loan %s was not found", id));
        return new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
    }
}
