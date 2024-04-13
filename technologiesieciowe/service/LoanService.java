package com.example.technologiesieciowe.service;

import com.example.technologiesieciowe.infrastructure.entity.*;
import com.example.technologiesieciowe.infrastructure.repository.BookRepository;
import com.example.technologiesieciowe.infrastructure.repository.LoanArchiveRepository;
import com.example.technologiesieciowe.infrastructure.repository.LoanRepository;
import com.example.technologiesieciowe.infrastructure.repository.UserRepository;
import com.example.technologiesieciowe.service.error.BookErrors.BookNotFoundException;
import com.example.technologiesieciowe.service.error.FieldRequiredException;
import com.example.technologiesieciowe.service.error.InvalidDateException;
import com.example.technologiesieciowe.service.error.LoanErrors.LoanNotFoundException;
import com.example.technologiesieciowe.service.error.LoanErrors.NotAvailableCopiesException;
import com.example.technologiesieciowe.service.error.ReviewErrors.ReviewNotFoundException;
import com.example.technologiesieciowe.service.error.UserErrors.UserAccessDeniedException;
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
    private final LoanArchiveService loanArchiveService;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookService bookService;

    @Autowired
    public LoanService(LoanRepository loanRepository, LoanArchiveService loanArchiveService, BookRepository bookRepository, UserRepository userRepository, BookService bookService) {
        this.loanRepository = loanRepository;
        this.loanArchiveService = loanArchiveService;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.bookService = bookService;
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
        if (loan.getBook() == null || loan.getBook().getId() == null) {
            throw FieldRequiredException.create("Book ID");
        } else if (loan.getUser() == null || loan.getUser().getUserId() == null) {
            throw FieldRequiredException.create("User ID");
        }

        if (loan.getLoanDate() == null) {
            loan.setLoanDate(LocalDate.now().toString());
        }
        validateLoanDate(loan.getLoanDate());
        if (loan.getDueDate() != null) {
            validateLoanDueDate(loan.getDueDate(), loan.getLoanDate());
        } else {
            LocalDate loanDate = LocalDate.parse(loan.getLoanDate(), DateTimeFormatter.ISO_DATE);
            LocalDate dueDate = loanDate.plusMonths(3);
            String dueDateStr = dueDate.format(DateTimeFormatter.ISO_DATE);
            loan.setDueDate(dueDateStr);
        }

        Optional<BookEntity> bookOptional = bookRepository.findById(loan.getBook().getId());
        if (bookOptional.isPresent()) {
            BookEntity book = bookOptional.get();
            if (bookService.isAvailable(book.getId())) {
                book.setAvailableCopies(book.getAvailableCopies() - 1);
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
        LoanEntity loan = loanRepository.findById(loanId)
                .orElseThrow(() -> LoanNotFoundException.create(loanId.toString()));
        String loggedInUserId = LoginService.getLoggedInUserId();
        String loggedInUserRole = LoginService.getLoggedInUserRole();
        if (!((loggedInUserRole.equals("ROLE_LIBRARIAN") || loggedInUserRole.equals("ROLE_ADMIN")) ||
                (loan.getUser().getUserId().toString().equals(loggedInUserId)))) {
            throw UserAccessDeniedException.create("You are not allowed to get information about this loan.");
        } else {
            return loan;
        }
    }

    public LoanEntity extendDueDateLoan(Integer loanId, LoanEntity editedLoan) {
        LoanEntity loanToEdit = loanRepository.findById(loanId)
                .orElseThrow(() -> LoanNotFoundException.create(loanId.toString()));

        String newDueDate = editedLoan.getDueDate();

        if (newDueDate != null) {
            validateLoanDueDate(newDueDate, loanToEdit.getLoanDate());
            loanToEdit.setDueDate(newDueDate);
        }

        return loanRepository.save(loanToEdit);
    }

    public void returnBook (Integer loanId, LoanEntity endedLoan) {
        LoanEntity loanToEdit = loanRepository.findById(loanId)
                .orElseThrow(() -> LoanNotFoundException.create(loanId.toString()));

        LoanArchiveEntity archivalLoan = new LoanArchiveEntity();
        if (endedLoan.getReturnDate() == null) {
            archivalLoan.setReturnDate(LocalDate.now().toString());
        } else {
            archivalLoan.setReturnDate(endedLoan.getReturnDate());
        }
        archivalLoan.setBook(loanToEdit.getBook());
        archivalLoan.setUser(loanToEdit.getUser());
        archivalLoan.setLoanDate(loanToEdit.getLoanDate());
        archivalLoan.setDueDate(loanToEdit.getDueDate());

        loanArchiveService.addLoanArchive(archivalLoan);
        loanRepository.deleteById(loanId);
    }


    private void validateLoanDate(String loanDate) {
        LocalDate currentDate = LocalDate.now();
        LocalDate parsedReviewDate;

        try {
            parsedReviewDate = LocalDate.parse(loanDate, DateTimeFormatter.ISO_DATE);
        } catch (DateTimeParseException e) {
            throw InvalidDateException.create("Invalid loan date format");
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
            throw InvalidDateException.create("Invalid due date format");
        }
        if (parsedReviewDate.isBefore(currentDate)) {
            throw InvalidDateException.create("Loan due date cannot be in the past");
        }
        if (parsedReviewDate.isBefore(parsedLoanDate)) {
            throw InvalidDateException.create("Loan due date cannot be before loan date");
        }
    }

    public boolean hasUserBorrowedBook(Integer userId, Integer bookId) {
        return loanRepository.existsByUserAndBook(userRepository.findById(userId), bookRepository.findById(bookId));
    }
}
