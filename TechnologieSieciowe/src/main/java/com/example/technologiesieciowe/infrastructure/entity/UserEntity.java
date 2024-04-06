package com.example.technologiesieciowe.infrastructure.entity;

import com.example.technologiesieciowe.commonTypes.UserRole;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class UserEntity {
    @Id@GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer userId;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<LoanEntity> loans;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ReviewEntity> reviews;
    @Basic
    @Column(name = "user_name", unique = true, nullable = false)
    private String userName;
    @Basic
    @Column(name = "user_password", nullable = false)
    private String userPassword;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;
    @Basic
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Basic
    @Column(name = "user_firstName", nullable = false)
    private String userFirstName;

    @Basic
    @Column(name = "user_lastName", nullable = false)
    private String userLastName;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<LoanEntity> getLoans() {
        return loans;
    }

    public void setLoans(List<LoanEntity> loans) {
        this.loans = loans;
    }

    public List<ReviewEntity> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewEntity> reviews) {
        this.reviews = reviews;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }
}
