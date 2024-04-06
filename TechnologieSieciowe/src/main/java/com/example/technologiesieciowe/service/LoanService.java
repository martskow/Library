package com.example.technologiesieciowe.service;

import com.example.technologiesieciowe.infrastructure.entity.BookEntity;
import com.example.technologiesieciowe.infrastructure.entity.LoanEntity;
import com.example.technologiesieciowe.infrastructure.entity.ReviewEntity;
import com.example.technologiesieciowe.infrastructure.repository.BookRepository;
import com.example.technologiesieciowe.infrastructure.repository.LoanRepository;
import com.example.technologiesieciowe.service.error.BookErrors.BookNotFoundException;
import com.example.technologiesieciowe.service.error.FieldRequiredException;
import com.example.technologiesieciowe.service.error.InvalidDateException;
import com.example.technologiesieciowe.service.error.LoanErrors.LoanNotFoundException;
import com.example.technologiesieciowe.service.error.LoanErrors.NotAvailableCopiesException;
import com.example.technologiesieciowe.service.error.ReviewErrors.ReviewNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
    }

    public List<LoanEntity> getAll() {
        return loanRepository.findAll();
    }

    public void delete(Integer id) {
        Optional<LoanEntity> loanOptional = loanRepository.findById(id);
        if (loanOptional.isPresent()) {
            loanRepository.deleteById(id);
        } else {
            throw LoanNotFoundException.create(id.toString());
        }
    }

    public LoanEntity addLoan(LoanEntity loan) {
        if (loan.getLoanDate() == null) {
            throw FieldRequiredException.create("Loan date");
        } else if (loan.getDueDate() == null) {
            throw FieldRequiredException.create("Due date");
        } else if (loan.getBook() == null) {
            throw FieldRequiredException.create("Book");
        } else if (loan.getUser() == null) {
            throw FieldRequiredException.create("User");
        }
        validateLoanDate(loan.getLoanDate());
        validateLoanDueDate(loan.getDueDate(), loan.getLoanDate());

        Optional<BookEntity> bookOptional = bookRepository.findById(loan.getBook().getId());
        if (bookOptional.isPresent()) {
            BookEntity book = bookOptional.get();
            int availableCopies = book.getAvailableCopies();
            if (availableCopies > 0) {
                book.setAvailableCopies(availableCopies - 1);
                bookRepository.save(book);
            } else {
                throw NotAvailableCopiesException.create(book.getTitle());
            }
        } else {
            throw BookNotFoundException.create(loan.getBook().getId().toString());
        }

        return loanRepository.save(loan);
    }

    public LoanEntity getOne(Integer loanId) {
        return loanRepository.findById(loanId).orElseThrow(() -> LoanNotFoundException.create(loanId.toString()));
    }

    private void validateLoanDate(String loanDate) {
        LocalDate currentDate = LocalDate.now();
        LocalDate parsedReviewDate;

        try {
            parsedReviewDate = LocalDate.parse(loanDate, DateTimeFormatter.ISO_DATE);
        } catch (DateTimeParseException e) {
            throw InvalidDateException.create("Invalid review date format");
        }
        if (parsedReviewDate.isBefore(currentDate)) {
            throw InvalidDateException.create("Loan date cannot be in the past");
        }
    }

    private void validateLoanDueDate(String dueDate, String loanDate) {
        LocalDate currentDate = LocalDate.now();
        LocalDate parsedReviewDate;
        LocalDate parsedLoanDate = LocalDate.parse(loanDate, DateTimeFormatter.ISO_DATE);

        try {
            parsedReviewDate = LocalDate.parse(dueDate, DateTimeFormatter.ISO_DATE);
        } catch (DateTimeParseException e) {
            throw InvalidDateException.create("Invalid review date format");
        }
        if (parsedReviewDate.isBefore(currentDate)) {
            throw InvalidDateException.create("Loan due date cannot be in the past");
        }
        if (parsedReviewDate.isBefore(parsedLoanDate)) {
            throw InvalidDateException.create("Loan due date cannot be before loan date");
        }
    }
}
