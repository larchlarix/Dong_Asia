package com.example.DongAisa.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;


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

    @Column(name="news_contents", unique = false, nullable = false,columnDefinition = "TEXT")
    private String newsContents;

    @Column(name = "news_link",unique = false,nullable = true)
    private String newsLink;

    @Column(name = "news_category", unique = false, nullable = true)
    private Long newsCategory;


    @Column(name="news_publisher", unique=false, nullable = true)
    private Long newsPublisher;


    @Column(name="news_image_url", unique=false, nullable=true)
    private String newsImageURL;


    @Column(name="news_country", nullable = false)
    private String newsCountry;

    public String getNewsCountry() {
        return newsCountry;
    }

    public void setNewsCountry(String newsCountry) {
        this.newsCountry = newsCountry;
    }

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

    public Long getNewsCategory() {
        return newsCategory;
    }

    public void setNewsCategory(Long newsCategory) {
        this.newsCategory = newsCategory;
    }

    public Long getNewsPublisher() {return newsPublisher; }

    public void setNewsPublisher(Long newsPublisher) {
        this.newsPublisher = newsPublisher;
    }

    public String getNewsImageURL() {
        return newsImageURL;
    }

    public void setNewsImageURL(String newsImageURL) {
        this.newsImageURL = newsImageURL;
    }

}
