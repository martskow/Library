package com.example.technologiesieciowe.infrastructure.entity;

import jakarta.persistence.*;

@Entity
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ReviewID")
    private Integer reviewId;

    @ManyToOne
    @JoinColumn(name = "BookID")
    private BookEntity book;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private UserEntity user;

    @Basic
    @Column(name = "Rating")
    private int rating;

    @Basic
    @Column(name = "Comment")
    private String comment;

    @Basic
    @Column(name = "ReviewDate")
    private String reviewDate;

//    public ReviewEntity () {
//        setDate(reviewDate);
//    }
//
//    public void setDate(String date) {
//        if (isValidDateFormat(date)) {
//            this.reviewDate = reviewDate;
//        } else {
//            throw new IllegalArgumentException("Invalid date format. Please use format DD-MM-YYYY");
//        }
//    }
//
//    private boolean isValidDateFormat(String date) {
//        String regex = "\\d{2}-\\d{2}-\\d{4}";
//        return date.matches(regex);
//    }

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }
}