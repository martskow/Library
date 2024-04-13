package com.example.technologiesieciowe.service;

import com.example.technologiesieciowe.infrastructure.entity.BookEntity;
import com.example.technologiesieciowe.infrastructure.entity.UserEntity;
import com.example.technologiesieciowe.infrastructure.repository.BookRepository;
import com.example.technologiesieciowe.service.error.BookErrors.BookNotFoundException;
import com.example.technologiesieciowe.service.error.BookErrors.IsbnAlreadyExistsException;
import com.example.technologiesieciowe.service.error.FieldRequiredException;
import com.example.technologiesieciowe.service.error.LoanErrors.NotAvailableCopiesException;
import com.example.technologiesieciowe.service.error.UserErrors.UserAccessDeniedException;
import com.example.technologiesieciowe.service.error.UserErrors.UserAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }
    public List<BookEntity> getAll() {
        return bookRepository.findAll();
    }
    public List<String> getAllTitlesWithAuthors() {
        List<BookEntity> books = bookRepository.findAll();
        return books.stream()
                .map(book -> book.getTitle() + ". " + book.getAuthor())
                .collect(Collectors.toList());
    }
    public BookEntity getOne(Integer id) {
        return bookRepository.findById(id).orElseThrow(() -> BookNotFoundException.create(id.toString()));
    }

    public Boolean isAvailable(Integer bookId) {
        try {
            BookEntity book = bookRepository.getById(bookId);
            int availableCopies = book.getAvailableCopies();
            return availableCopies > 0;
        } catch (EntityNotFoundException e) {
            throw BookNotFoundException.create(bookId.toString());
        }
    }
    public BookEntity addBook(BookEntity book) {
        if (book.getIsbn() == null) {
            throw FieldRequiredException.create("ISBN");
        } else if (book.getAuthor() == null) {
            throw FieldRequiredException.create("Author");
        } else if (book.getTitle() == null) {
            throw FieldRequiredException.create("Title");
        } else if (book.getAvailableCopies() == null) {
            throw FieldRequiredException.create("Number of available copies");
        }
        Optional<BookEntity> existingBook = Optional.ofNullable(bookRepository.getByIsbn(book.getIsbn()));
        if (existingBook.isPresent()) {
            throw IsbnAlreadyExistsException.create(book.getIsbn());
        }
        return bookRepository.save(book);
    }

    public BookEntity editBook(Integer bookId, BookEntity editedBook) {
        BookEntity bookToEdit = bookRepository.findById(bookId)
                .orElseThrow(() -> BookNotFoundException.create(bookId.toString()));


        String loggedInUserRole = LoginService.getLoggedInUserRole();
        if (loggedInUserRole != null && !loggedInUserRole.equals("ROLE_LIBRARIAN")) {
            throw UserAccessDeniedException.create("You are not allowed to edit information about books.");
        }

        String newIsbn = editedBook.getIsbn();
        String newTitle = editedBook.getTitle();
        String newAuthor = editedBook.getAuthor();
        String newPublisher = editedBook.getPublisher();
        String newPublishYear = editedBook.getPublishYear();
        Integer newAvailableCopies = editedBook.getAvailableCopies();

        if (newIsbn != null) {
            bookToEdit.setIsbn(newIsbn);
        }
        if (newTitle != null) {
            bookToEdit.setTitle(newTitle);
        }
        if (newAuthor != null) {
            bookToEdit.setAuthor(newAuthor);
        }
        if (newPublisher != null) {
            bookToEdit.setPublisher(newPublisher);
        }
        if (newPublishYear != null) {
            bookToEdit.setPublishYear(newPublishYear);
        }
        if (newAvailableCopies != null) {
            bookToEdit.setAvailableCopies(newAvailableCopies);
        }

        return bookRepository.save(bookToEdit);
    }

    public void delete(Integer id) {
        Optional<BookEntity> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            bookRepository.deleteById(id);
        } else {
            throw BookNotFoundException.create(id.toString());
        }
    }
}
