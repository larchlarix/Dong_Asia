package com.example.DongAisa.domain;

import jakarta.persistence.*;
@Entity
@Table(name="newsTest", schema="dong_asia", uniqueConstraints = {@UniqueConstraint(columnNames = "newsTest_id")})
public class NewsTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "newsTest_id", unique = true, nullable = false)
    private Long newsTestId;

    @Column(name = "news_title", unique = false, nullable = false)
    private String newsTitle;


    @Column(name = "news_date", unique = false, nullable = true)
    private String newsDate;
    @Column(name = "news_contents", unique = true, nullable = false, columnDefinition = "TEXT")
    private String newsContents;

    @Column(name = "news_link", unique = true, nullable = true)
    private String newsLink;

    @Column(name = "news_category", unique = false, nullable = true)
    private Long newsCategory;


    @Column(name = "news_publisher", unique = false, nullable = true)
    private Long newsPublisher;


    @Column(name = "news_image_url", unique = false, nullable = true)
    private String newsImageURL;


    @Column(name = "news_country", nullable = false)
    private String newsCountry;


    public NewsTest() {
    }

    ;

    public Long getNewsTestId() {
        return newsTestId;
    }

    public void setNewsTestId(Long newsTestId) {
        this.newsTestId = newsTestId;
    }

}