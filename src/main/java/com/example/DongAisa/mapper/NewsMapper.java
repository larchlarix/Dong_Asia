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
        return newsDto;

    }
    public static News convertToModel(NewsDto newsDto){
        News news = new News();
        news.setNewsId(newsDto.getNewsId());
        news.setNewsTitle(newsDto.getNewsTitle());
        news.setNewsContents(newsDto.getNewsContents());
        news.setNewsDate(newsDto.getNewsDate());
        news.setNewsLink(newsDto.getNewsLink());
        return  news;
    }

    public static List<NewsDto> convertToDtoList(List<News> news){
        return news.stream().map(NewsMapper::convertToDto).collect(Collectors.toList());
    }

}
