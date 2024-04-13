package com.example.technologiesieciowe.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "loans", schema = "library")
public class LoanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "loan_id")
    private Integer loanId;

    @ManyToOne
    @JsonIgnoreProperties({"loans", "archiveLoans", "reviews", "role", "userPassword", "email", "userFirstName", "userLastName"})
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;

    @ManyToOne
    @JsonIgnoreProperties({"loans", "archiveLoans", "reviews", "isbn", "author", "publisher", "publishYear", "availableCopies"})
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Basic
    @Column(name = "loan_date", nullable = false)
    private String loanDate;

    @Column(name = "due_date", nullable = false)
    private String dueDate;

    @Column(name = "return_date")
    private String returnDate;


    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
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

    public String getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
}
