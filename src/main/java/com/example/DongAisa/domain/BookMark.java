package com.example.DongAisa.domain;


import jakarta.persistence.*;
import lombok.Builder;

import static jakarta.persistence.FetchType.LAZY;

@Builder
@Entity
@Table(name="bookMark")
public class BookMark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bookMark_id")
    private Long bookMarkId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="news_id")
    private News news;

    public BookMark(){};

    @Builder
    public BookMark(Long bookMarkId, User user, News news) {
        this.bookMarkId = bookMarkId;
        this.user = user;
        this.news = news;
    }
    public Long getBookMarkId() {
        return bookMarkId;
    }

    public void setBookMarkId(Long bookMarkId) {
        this.bookMarkId = bookMarkId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }




}
