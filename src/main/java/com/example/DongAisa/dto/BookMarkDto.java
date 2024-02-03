package com.example.DongAisa.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;


public class BookMarkDto {
    private Long userId;
    private Long newsId;

    private String newsTitle; // 추가
    private String newsDate; // 추가

    private Long newsPublisher;

    private String newsImageURL;



    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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

    public Long getNewsPublisher() {
        return newsPublisher;
    }

    public void setNewsPublisher(Long newsPublisher) {
        this.newsPublisher = newsPublisher;
    }

    public String getNewsImageURL() {
        return newsImageURL;
    }

    public void setNewsImageURL(String newsImageURL) {
        this.newsImageURL = newsImageURL;
    }

    @JsonCreator
    public BookMarkDto(@JsonProperty("userId") Long userId, @JsonProperty("newsId") Long newsId, @JsonProperty("newsTitle") String newsTitle,
                       @JsonProperty("newsDate") String newsDate, @JsonProperty("newsImageURL") String newsImageURL, @JsonProperty("newsPublisher") Long newsPublisher) {
        this.userId = userId;
        this.newsId = newsId;
        this.newsTitle = newsTitle;
        this.newsDate = newsDate;
        this.newsImageURL = newsImageURL;
        this.newsPublisher = newsPublisher;
    }
}
