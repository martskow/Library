package com.example.technologiesieciowe.service.error.LoanErrors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotAvailableCopiesException extends RuntimeException {
    private NotAvailableCopiesException(String message) {
        super(message);
    }

    public static ResponseStatusException create(String bookName) {
        NotAvailableCopiesException exception = new NotAvailableCopiesException(String.format("There are currently no copies of the book %s available", bookName));
        return new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage(), exception);
    }
}
