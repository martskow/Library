package com.example.technologiesieciowe.service;

import com.example.technologiesieciowe.infrastructure.entity.LoanEntity;
import com.example.technologiesieciowe.infrastructure.entity.ReviewEntity;
import com.example.technologiesieciowe.infrastructure.entity.UserEntity;
import com.example.technologiesieciowe.infrastructure.repository.ReviewRepository;
import com.example.technologiesieciowe.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public ReviewEntity addReview(ReviewEntity review) {
        return reviewRepository.save(review);
    }

    public List<ReviewEntity> getAll() {
        return reviewRepository.findAll();
    }
    public ReviewEntity getOne(Integer reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("Review not found."));
    }

    public void delete(Integer id) {
        reviewRepository.deleteById(id);
    }
}
