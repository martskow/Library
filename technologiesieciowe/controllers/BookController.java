package com.example.technologiesieciowe.controllers;

import com.example.technologiesieciowe.infrastructure.entity.BookEntity;
import com.example.technologiesieciowe.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return bookService.addBook(book);
    }

    @PutMapping("/edit/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public BookEntity editBook(@PathVariable Integer id, @RequestBody BookEntity editedBook) {
        return bookService.editBook(id, editedBook);
    }

    @GetMapping("/getAll")
    @ResponseStatus(code = HttpStatus.OK)
    public @ResponseBody Iterable<BookEntity> getAllBooks(){
        return bookService.getAll();
    }

    @GetMapping("/getOne/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public BookEntity getOne (@PathVariable Integer id) {
        return bookService.getOne(id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void delete(@PathVariable Integer id) {
        bookService.delete(id);
    }

    @GetMapping("/isAvailable/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public @ResponseBody Boolean isAvailable(@PathVariable Integer id){
        return bookService.isAvailable(id);
    }

    @GetMapping("/getAllTitles")
    @ResponseStatus(code = HttpStatus.OK)
    public @ResponseBody List<String> getAllTitles() {
        return bookService.getAllTitlesWithAuthors();
    }

}
