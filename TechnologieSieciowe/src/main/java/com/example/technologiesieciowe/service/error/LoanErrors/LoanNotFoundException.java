package com.example.technologiesieciowe.service.error.LoanErrors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LoanNotFoundException extends RuntimeException {
    private LoanNotFoundException(String message) {
        super(message);
    }

    public static ResponseStatusException create(String id) {
        LoanNotFoundException exception = new LoanNotFoundException(String.format("Loan %s was not found", id));
        return new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
    }
}
