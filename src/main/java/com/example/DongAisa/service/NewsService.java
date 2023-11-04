package com.example.DongAisa.service;

import com.example.DongAisa.domain.News;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import com.example.DongAisa.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;

    public News getNews(Long newsId){
        Optional<News> res = newsRepository.findById(newsId);
        if(res.isPresent()){
            return res.get();
        }else{
            throw new EntityNotFoundException(String.format("앨범 아이디 %d로 조회되지 않았습니다", newsId));

        }
    }
}
