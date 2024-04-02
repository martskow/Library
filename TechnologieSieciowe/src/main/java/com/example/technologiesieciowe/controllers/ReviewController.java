package com.example.technologiesieciowe.controllers;

import com.example.technologiesieciowe.infrastructure.entity.BookEntity;
import com.example.technologiesieciowe.infrastructure.entity.ReviewEntity;
import com.example.technologiesieciowe.service.BookService;
import com.example.technologiesieciowe.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ReviewEntity addReview(@RequestBody ReviewEntity review){
        return reviewService.addReview(review);
    }

    @GetMapping("/getAll")
    public @ResponseBody Iterable<ReviewEntity> getAllBooks(){
        return reviewService.getAll();
    }

    @GetMapping("/getOne/{id}")
    public ReviewEntity getOne (@PathVariable Integer id) {
        return reviewService.getOne(id);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        reviewService.delete(id);
    }
}
