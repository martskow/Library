package com.example.technologiesieciowe.controllers;

import com.example.technologiesieciowe.infrastructure.entity.BookDetailsEntity;
import com.example.technologiesieciowe.infrastructure.entity.BookEntity;
import com.example.technologiesieciowe.service.BookDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookDetails")
public class BookDetailsController {
    private final BookDetailsService bookDetailsService;

    @Autowired
    public BookDetailsController(BookDetailsService bookDetailsService){
        this.bookDetailsService = bookDetailsService;
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public BookDetailsEntity save(@RequestBody BookDetailsEntity book){
        return bookDetailsService.save(book);
    }

    @GetMapping("/getAll")
    @ResponseStatus(code = HttpStatus.OK)
    public @ResponseBody Iterable<BookDetailsEntity> getAllBooks(){
        return bookDetailsService.getAll();
    }

    @GetMapping("/getOne/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public BookEntity getOne (@PathVariable Integer id) {
        return bookDetailsService.getOne(id).getBook();
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void delete(@PathVariable Integer id) {
        bookDetailsService.delete(id);
    }
}

