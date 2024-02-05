package com.example.DongAisa.mapper;

import com.example.DongAisa.domain.News;

import com.example.DongAisa.dto.NewsDto;


import java.util.List;
import java.util.stream.Collectors;

public class NewsMapper {
    public static NewsDto convertToDto(News news){
        NewsDto newsDto = new NewsDto();
        newsDto.setNewsId(news.getNewsId());
        newsDto.setNewsTitle(news.getNewsTitle());
        newsDto.setNewsContents(news.getNewsContents());
        newsDto.setNewsDate(news.getNewsDate());
        newsDto.setNewsLink(news.getNewsLink());
        newsDto.setNewsCategory(news.getNewsCategory());
        newsDto.setNewsImageURL(news.getNewsImageURL());
        newsDto.setNewsPublisher(news.getNewsPublisher());
        newsDto.setLikeCount(news.getLikeCount());
        newsDto.setNewsCountry(news.getNewsCountry());
        return newsDto;

    }
    public static News convertToModel(NewsDto newsDto){
        News news = new News();
        news.setNewsId(newsDto.getNewsId());
        news.setNewsTitle(newsDto.getNewsTitle());
        news.setNewsContents(newsDto.getNewsContents());
        news.setNewsDate(newsDto.getNewsDate());
        news.setNewsLink(newsDto.getNewsLink());
        news.setNewsCategory(newsDto.getNewsCategory());
        news.setNewsImageURL(newsDto.getNewsImageURL());
        news.setNewsPublisher(newsDto.getNewsPublisher());
        news.setLikeCount(newsDto.getLikeCount());
        news.setNewsCountry(newsDto.getNewsCountry());
        return  news;
    }

    public static List<NewsDto> convertToDtoList(List<News> news){
        return news.stream().map(NewsMapper::convertToDto).collect(Collectors.toList());
    }

}
