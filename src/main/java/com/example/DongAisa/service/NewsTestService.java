package com.example.DongAisa.service;

import com.example.DongAisa.domain.News;
import com.example.DongAisa.domain.NewsTest;
import com.example.DongAisa.repository.NewsRepository;
import com.example.DongAisa.repository.NewsTestRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NewsTestService {
    @Autowired
    private NewsTestRepository newsTestRepository;

    public NewsTest getNewsTest(Long newsId){
        Optional<NewsTest> res = newsTestRepository.findById(newsId);
        if(res.isPresent()){
            return res.get();
        }else{
            throw new EntityNotFoundException(String.format("뉴스 아이디 %d로 조회되지 않았습니다", newsId));

        }
    }
}
