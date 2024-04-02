package com.example.technologiesieciowe.controllers;

import com.example.technologiesieciowe.infrastructure.entity.BookEntity;
import com.example.technologiesieciowe.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.technologiesieciowe.infrastructure.repository.BookRepository;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public BookEntity addBook(@RequestBody BookEntity book){
        return bookService.save(book);
    }

    @GetMapping("/getAll")
    public @ResponseBody Iterable<BookEntity> getAllBooks(){
        return bookService.getAll();
    }

    @GetMapping("/getOne/{id}")
    public BookEntity getOne (@PathVariable Integer id) {
        return bookService.getOne(id);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        bookService.delete(id);
    }
}
