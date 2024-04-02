package com.example.technologiesieciowe.infrastructure.entity;

import jakarta.persistence.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "loans", schema = "library")
public class LoanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "LoanId")
    private Integer LoanId;

    @ManyToOne
    @JoinColumn(name = "BookID")
    private BookEntity book;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private UserEntity user;

    @Basic
    @Column(name = "LoanDate")
    private String loanDate;

    @Column(name = "DueDate")
    private String dueDate;

    @Column(name = "ReturnDate")
    private String returnDate;
//
//    public LoanEntity() {
//        setDate(loanDate, "loanDate");
//        setDate(dueDate, "dueDate");
//        if (returnDate != null) {
//            setDate(returnDate, "returnDate");
//        }
//        if (!isValidLoanDate(loanDate, dueDate)) {
//            throw new IllegalArgumentException("Loan date must be before or equal to due date.");
//        }
//    }
//
//    public void setDate(String date, String typeOfDate) {
//        if (isValidDateFormat(date)) {
//            if ("loanDate".equals(typeOfDate)) {
//                this.loanDate = date;
//            } else if ("dueDate".equals(typeOfDate)) {
//                this.dueDate = date;
//            } else if ("returnDate".equals(typeOfDate)) {
//                this.returnDate = date;
//            }
//        } else {
//            throw new IllegalArgumentException("Invalid date format. Please use format DD-MM-YYYY");
//        }
//    }
//
//    private boolean isValidDateFormat(String date) {
//        String regex = "\\d{2}-\\d{2}-\\d{4}";
//        return date.matches(regex);
//    }
//
//    private boolean isValidLoanDate(String loanDate, String dueDate) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//        try {
//            Date loanDateObj = dateFormat.parse(loanDate);
//            Date dueDateObj = dateFormat.parse(dueDate);
//            return !loanDateObj.after(dueDateObj);
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

    public Integer getLoanId() {
        return LoanId;
    }

    public void setLoanId(Integer loanId) {
        LoanId = loanId;
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
