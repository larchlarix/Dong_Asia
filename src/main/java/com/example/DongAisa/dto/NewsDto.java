package com.example.DongAisa.dto;

public class NewsDto {
    Long newsId;
    String newsTitle;

    String newsDate;

    String newsContents;

    String newsLink;

    Long newsCategory;

    String newsImageURL;

    Long newsPublisher;

    Integer LikeCount;

    String newsCountry;


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

    public Integer getLikeCount() {
        return LikeCount;
    }

    public void setLikeCount(Integer likeCount) {
        LikeCount = likeCount;
    }

    public String getNewsCountry() {
        return newsCountry;
    }

    public void setNewsCountry(String newsCountry) {
        this.newsCountry = newsCountry;
    }
}
