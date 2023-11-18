package com.example.DongAisa.domain;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Entity
@Table(name="news", schema="dong_asia", uniqueConstraints = {@UniqueConstraint(columnNames = "news_id")})
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id", unique = true, nullable = false)
    private Long newsId;

    @Column(name="news_title",unique = false, nullable = false)
    private String newsTitle;


    @Column(name= "news_date", unique = false, nullable = true)
    private String newsDate;

    @Column(name="news_contents", unique = true, nullable = false)
    private String newsContents;

    @Column(name = "news_link",unique = true,nullable = false)
    private String newsLink;


    public News(){};


    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }


    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }

    public String getNewsContents() {
        return newsContents;
    }

    public void setNewsContents(String newsContents) {
        this.newsContents = newsContents;
    }

    public String getNewsLink() {
        return newsLink;
    }

    public void setNewsLink(String newsLink) {
        this.newsLink = newsLink;
    }

}
