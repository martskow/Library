package com.example.technologiesieciowe.service;

import com.example.technologiesieciowe.infrastructure.entity.BookEntity;
import com.example.technologiesieciowe.infrastructure.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found."));
    }
    public BookEntity save(BookEntity book) {
        return bookRepository.save(book);
    }

    public void delete(Integer id) {
        bookRepository.deleteById(id);
    }
}
