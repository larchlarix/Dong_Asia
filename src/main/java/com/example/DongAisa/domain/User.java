package com.example.DongAisa.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
@Entity
@Table(name="user",schema = "dong_asia", uniqueConstraints = {@UniqueConstraint(columnNames = "user_id")})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id", unique = true, nullable = false)
    private Long userId;

    @Column(name="user_email", unique = true, nullable = false)
    private String userEmail;

    @Column(name="user_name", unique = false, nullable = false)
    private String userName;

    @Column(name="user_password", unique = false, nullable = false)
    private  String userPassword;



    public User(){};

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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



}
