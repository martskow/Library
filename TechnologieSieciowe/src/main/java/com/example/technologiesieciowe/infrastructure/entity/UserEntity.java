package com.example.technologiesieciowe.infrastructure.entity;

import com.example.technologiesieciowe.commonTypes.UserRole;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class UserEntity {
    @Id@GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name = "UserId")
    private Integer UserId;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<LoanEntity> loans;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ReviewEntity> reviews;
    @Basic
    @Column(name = "UserName", unique = true)
    private String UserName;
    @Basic
    @Column(name = "UserPassword")
    private String UserPassword;
    @Enumerated(EnumType.STRING)
    @Column(name = "Role")
    private UserRole Role;
    @Basic
    @Column(name = "Email", unique = true)
    private String Email;

    @Basic
    @Column(name = "UserFullName")
    private String UserFullName;

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

    public UserRole getRole() {
        return Role;
    }

    public void setRole(UserRole role) {
        Role = role;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUserFullName() {
        return UserFullName;
    }

    public void setUserFullName(String userFullName) {
        UserFullName = userFullName;
    }
}
