package com.example.technologiesieciowe.service;

import com.example.technologiesieciowe.infrastructure.entity.ReviewEntity;
import com.example.technologiesieciowe.infrastructure.repository.ReviewRepository;
import com.example.technologiesieciowe.service.error.FieldRequiredException;
import com.example.technologiesieciowe.service.error.InvalidDateException;
import com.example.technologiesieciowe.service.error.ReviewErrors.RatingOffTheScaleException;
import com.example.technologiesieciowe.service.error.ReviewErrors.ReviewNotFoundException;
import com.example.technologiesieciowe.service.error.UserErrors.UserAccessDeniedException;
import com.example.technologiesieciowe.service.error.UserErrors.UserNotBorrowedBookException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;


@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final LoanService loanService;
    private final LoanService loanArchiveService;

    public ReviewService(ReviewRepository reviewRepository, LoanService loanService, LoanService loanArchiveService) {
        this.reviewRepository = reviewRepository;
        this.loanService = loanService;
        this.loanArchiveService = loanArchiveService;
    }

    public ReviewEntity addReview(ReviewEntity review) {
        if (review.getBook() == null || review.getBook().getId() == null) {
            throw FieldRequiredException.create("Book ID");
        } else if (review.getUser() == null || review.getUser().getUserId() == null) {
            throw FieldRequiredException.create("User ID");
        } else if (review.getRating() == null) {
            throw FieldRequiredException.create("Rating");
        } else {
            if (review.getReviewDate() == null) {
                LocalDate today = LocalDate.now();
                review.setReviewDate(today.toString());
            }
            validateRating(review.getRating());
            validateReviewDate(review.getReviewDate());

            if (!(loanService.hasUserBorrowedBook(review.getUser().getUserId(), review.getBook().getId())
                    || loanArchiveService.hasUserBorrowedBook(review.getUser().getUserId(), review.getBook().getId()))) {
                throw UserNotBorrowedBookException.create(review.getBook().getId().toString());
            }

            return reviewRepository.save(review);
        }
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

    public Iterable<ReviewEntity> getReviewsByUser(Integer userId) {
        return reviewRepository.findByUserUserId(userId);
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

    private void validateRating(Integer rating) {
        if (rating < 1 || rating > 10) {
            throw RatingOffTheScaleException.create(rating.toString());
        }
    }

    public ReviewEntity editReview(Integer reviewId, ReviewEntity editedReview) {
        ReviewEntity reviewToEdit = reviewRepository.findById(reviewId)
                .orElseThrow(() -> ReviewNotFoundException.create(reviewId.toString()));

        String loggedInUsername = LoginService.getLoggedInUserId();
        if (!reviewToEdit.getUser().getUserId().toString().equals(loggedInUsername)) {
            throw UserAccessDeniedException.create("You are not allowed to edit this review.");
        }

        Integer newRating = editedReview.getRating();
        String newComment = editedReview.getComment();
        String newReviewDate = editedReview.getReviewDate();

        if (newRating != null) {
            validateRating(newRating);
            reviewToEdit.setRating(newRating);
        }
        if (newComment != null) {
            reviewToEdit.setComment(newComment);
        }
        if (newReviewDate != null) {
            validateReviewDate(newReviewDate);
            reviewToEdit.setReviewDate(newReviewDate);
        }

        return reviewRepository.save(reviewToEdit);
    }
}
