package com.example.technologiesieciowe.service;

import com.example.technologiesieciowe.infrastructure.entity.BookEntity;
import com.example.technologiesieciowe.infrastructure.entity.UserEntity;
import com.example.technologiesieciowe.infrastructure.repository.BookRepository;
import com.example.technologiesieciowe.service.error.BookErrors.BookNotFoundException;
import com.example.technologiesieciowe.service.error.BookErrors.IsbnAlreadyExistsException;
import com.example.technologiesieciowe.service.error.FieldRequiredException;
import com.example.technologiesieciowe.service.error.UserErrors.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public BookEntity getOne(Integer id) {
        return bookRepository.findById(id).orElseThrow(() -> BookNotFoundException.create(id.toString()));
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

    public void delete(Integer id) {
        Optional<BookEntity> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            bookRepository.deleteById(id);
        } else {
            throw BookNotFoundException.create(id.toString());
        }
    }
}
