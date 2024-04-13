package com.example.technologiesieciowe.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "queue", schema = "library")
public class QueueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "queue_id")
    private Integer queueId;

    @ManyToOne
    @JsonIgnoreProperties({"loans", "archiveLoans", "reviews", "role", "userPassword", "email", "userFirstName", "userLastName"})
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;

    @ManyToOne
    @JsonIgnoreProperties({"loans", "archiveLoans", "reviews", "isbn", "author", "publisher", "publishYear", "availableCopies"})
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "queuing_date")
    private String queuingDate;

    public Integer getQueueId() {
        return queueId;
    }

    public void setQueueId(Integer queueId) {
        this.queueId = queueId;
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

    public String getQueuingDate() {
        return queuingDate;
    }

    public void setQueuingDate(String queuingDate) {
        this.queuingDate = queuingDate;
    }
}
