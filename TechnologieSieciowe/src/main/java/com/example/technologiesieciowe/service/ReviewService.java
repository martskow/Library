package com.example.technologiesieciowe.service;

import com.example.technologiesieciowe.infrastructure.entity.ReviewEntity;
import com.example.technologiesieciowe.infrastructure.repository.ReviewRepository;
import com.example.technologiesieciowe.service.error.FieldRequiredException;
import com.example.technologiesieciowe.service.error.InvalidDateException;
import com.example.technologiesieciowe.service.error.ReviewErrors.ReviewNotFoundException;
import com.example.technologiesieciowe.service.error.UserErrors.UserAccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserService userService;

    public ReviewService(ReviewRepository reviewRepository, UserService userService) {
        this.reviewRepository = reviewRepository;
        this.userService = userService;
    }

    public ReviewEntity addReview(ReviewEntity review) {
        if (review.getReviewDate() == null) {
            throw FieldRequiredException.create("Review date");
        } else if (review.getBook() == null) {
            throw FieldRequiredException.create("Book");
        } else if (review.getUser() == null) {
            throw FieldRequiredException.create("User");
        } else if (review.getRating() == null) {
            throw FieldRequiredException.create("Rating");
        }
        validateReviewDate(review.getReviewDate());

        return reviewRepository.save(review);
    }

    public List<ReviewEntity> getAll() {
        return reviewRepository.findAll();
    }
    public ReviewEntity getOne(Integer reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> ReviewNotFoundException.create(reviewId.toString()));
    }

    public void delete(Integer id) {
        Optional<ReviewEntity> reviewOptional = reviewRepository.findById(id);
        if (reviewOptional.isPresent()) {
            reviewRepository.deleteById(id);
        } else {
            throw ReviewNotFoundException.create(id.toString());
        }
    }

    public Iterable<ReviewEntity> getReviewsByBook(Integer bookId) {
        return reviewRepository.findByBookId(bookId);
    }

    private void validateReviewDate(String reviewDate) {
        LocalDate currentDate = LocalDate.now();
        LocalDate parsedReviewDate;

        try {
            parsedReviewDate = LocalDate.parse(reviewDate, DateTimeFormatter.ISO_DATE);
        } catch (DateTimeParseException e) {
            throw InvalidDateException.create("Invalid review date format");
        }
        if (parsedReviewDate.isAfter(currentDate)) {
            throw InvalidDateException.create("Review date cannot be in the future");
        }
    }

    public ReviewEntity editReview(Integer reviewId, ReviewEntity editedReview) {
        ReviewEntity reviewToEdit = reviewRepository.findById(reviewId)
                .orElseThrow(() -> ReviewNotFoundException.create(reviewId.toString()));

        String loggedInUsername = LoginService.getLoggedInUsername();
        if (!reviewToEdit.getUser().getUserId().toString().equals(loggedInUsername)) {
            throw UserAccessDeniedException.create("You are not allowed to edit this review.");
        }

        Integer newRating = editedReview.getRating();
        String newComment = editedReview.getComment();
        String newReviewDate = editedReview.getReviewDate();

        if (newRating != null) {
            reviewToEdit.setRating(newRating);
        }
        if (newComment != null) {
            reviewToEdit.setComment(newComment);
        }
        if (newReviewDate != null) {
            reviewToEdit.setReviewDate(newReviewDate);
        }

        return reviewRepository.save(reviewToEdit);
    }
}
