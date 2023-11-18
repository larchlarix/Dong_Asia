package com.example.DongAisa.service;

import com.example.DongAisa.domain.News;
import com.example.DongAisa.dto.NewsDto;
import com.example.DongAisa.mapper.NewsMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import com.example.DongAisa.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;

    public NewsDto getNews(Long newsId){
        Optional<News> res = newsRepository.findById(newsId);
        if(res.isPresent()){
            NewsDto newsDto = NewsMapper.convertToDto(res.get());
            return newsDto;
        }else{
            throw new EntityNotFoundException(String.format("News 아이디 %d로 조회되지 않았습니다", newsId));

        }
    }

    public NewsDto deleteNews(Long newsId){
        Optional<News> news = newsRepository.findById(newsId);
        if(news.isEmpty()){
            throw new NoSuchElementException(String.format("News ID '%d' 가 존재하지 않습니다", newsId));
        }
        newsRepository.deleteById(newsId);
        return NewsMapper.convertToDto(news.get());
    }

    public List<NewsDto> getNewsList(String keyword, String sort) {
        List<News> news;

        if (Objects.equals(sort, "byNewsDate")) {
            news = newsRepository.findByNewsTitleContainingOrderByNewsDateAsc(keyword);
        } else {
            throw new IllegalArgumentException("알 수 없는 정렬 기준입니다");
        }
        List<NewsDto> newsDtos = NewsMapper.convertToDtoList(news);
        return newsDtos;
    }


}
