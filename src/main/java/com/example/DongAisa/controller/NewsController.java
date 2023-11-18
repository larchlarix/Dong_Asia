package com.example.DongAisa.controller;

import com.example.DongAisa.dto.NewsDto;
import com.example.DongAisa.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    NewsService newsService;

    //뉴스 조회
    @RequestMapping(value="/{newsId}", method = RequestMethod.GET)
    public ResponseEntity<NewsDto> getNews(@PathVariable("newsId") final long newsId) {
        NewsDto news = newsService.getNews(newsId);
        return new ResponseEntity<>(news, HttpStatus.OK);
    }


    /*뉴스 생성
    @RequestMapping(value="",method = RequestMethod.POST)
    public ResponseEntity<NewsDto> createNews(@RequestBody final NewsDto newsDto) throws IOException {
    NewsDto savedNewsDto = newsService.createNews(newsDto);
    return new ResponseEntity<>(savedNewsDto, HttpStatus.OK);
    }
    */

    //뉴스 목록 조회
    @RequestMapping(value="",method= RequestMethod.GET)
    public ResponseEntity<List<NewsDto>>
    getNewsList(@RequestParam(value ="keyword", required = false,defaultValue ="" )final String keyword,
                @RequestParam(value = "sort", required = false,defaultValue = "byDate") final String sort){
        List<NewsDto> newsDtos = newsService.getNewsList(keyword, sort);
        return new ResponseEntity<>(newsDtos, HttpStatus.OK);
    }



    //뉴스 삭제
    @RequestMapping(value="/{newsId}", method = RequestMethod.DELETE)
    public ResponseEntity <NewsDto> deleteNews(@PathVariable("newsId") final long newsId){
        NewsDto deleteNewsDto = newsService.deleteNews(newsId);
        return new ResponseEntity<>(deleteNewsDto, HttpStatus.OK);
    }
}
