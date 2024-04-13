package com.example.technologiesieciowe.infrastructure.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "loans_archive", schema = "library")
public class LoanArchiveEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "loanarchive_id")
    private Integer LoanArchiveId;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Basic
    @Column(name = "loan_date", nullable = false)
    private String loanDate;

    @Column(name = "due_date", nullable = false)
    private String dueDate;

    @Column(name = "return_date", nullable = false)
    private String returnDate;

    @Column(name = "isAfterdue_date")
    private Boolean isAfterDueDate;

    public Integer getLoanArchiveId() {
        return LoanArchiveId;
    }

    public void setLoanArchiveId(Integer loanArchiveId) {
        LoanArchiveId = loanArchiveId;
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

    public Boolean getIsAfterDueDate() {
        return isAfterDueDate;
    }

    public void setIsAfterDueDate(Boolean isAfterDueDate) {
        this.isAfterDueDate = isAfterDueDate;
    }
}
