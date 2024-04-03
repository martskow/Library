package com.example.technologiesieciowe.service;

import com.example.technologiesieciowe.infrastructure.entity.BookDetailsEntity;
import com.example.technologiesieciowe.infrastructure.repository.BookDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookDetailsService {
    private final BookDetailsRepository bookDetailsRepository;

    @Autowired
    public BookDetailsService(BookDetailsRepository bookDetailsRepository) {
        this.bookDetailsRepository = bookDetailsRepository;
    }

    public List<BookDetailsEntity> getAll() {
        return bookDetailsRepository.findAll();
    }

    public BookDetailsEntity getOne(Integer id) {
        return bookDetailsRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found."));
    }

    public BookDetailsEntity save(BookDetailsEntity details) {
        return bookDetailsRepository.save(details);
    }

    public void delete(Integer id) {
        bookDetailsRepository.deleteById(id);
    }
}
