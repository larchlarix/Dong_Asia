package com.example.DongAisa.domain;

import com.example.DongAisa.constant.Role;
import com.example.DongAisa.dto.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="news_id")
    private News news;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static User createUser(UserDto userDto, PasswordEncoder passwordEncoder){
        User user = new User();
        user.setUserEmail(userDto.getUserEmail());
        user.setUserName(userDto.getUserName());
        String userPassword = passwordEncoder.encode(userDto.getUserPassword());
        user.setUserPassword((userPassword));
        user.setRole(Role.USER);
        return user;
    }



    public User(){};

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }


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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
