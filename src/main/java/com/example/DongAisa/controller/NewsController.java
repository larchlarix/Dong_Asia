package com.example.DongAisa.controller;


import com.example.DongAisa.dto.NewsDto;
import com.example.DongAisa.service.NewsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.util.List;

@Controller
@RequestMapping("/news")
public class NewsController {

    @Autowired
    NewsService newsService;

    /*뉴스 조회
    @RequestMapping(value="/{newsId}", method = RequestMethod.GET)
    public ResponseEntity<NewsDto> getNews(@PathVariable("newsId") final long newsId) {
        NewsDto news = newsService.getNews(newsId);
        return new ResponseEntity<>(news, HttpStatus.OK);
    }
*/
    /*
    @RequestMapping(value="/{newsId}", method = RequestMethod.GET)
    public ResponseEntity<NewsDto> getNews(@PathVariable("newsId") final long newsId) {
        try {
            NewsDto news = newsService.getNews(newsId);
            return new ResponseEntity<>(news, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            // 뉴스가 존재하지 않는 경우
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // 다른 예외 발생 시
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
*/
    @RequestMapping(value="/{newsId}", method = RequestMethod.GET)
    public String getNews(Model model, @PathVariable("newsId") final long newsId){
        try {
            NewsDto news = newsService.getNews(newsId);
            model.addAttribute("news", news);
            return "main_text";
        } catch (EntityNotFoundException e){
            // 뉴스가 존재하지 않는 경우
            return String.valueOf(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            // 다른 예외 발생 시
            return String.valueOf(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }



    /*뉴스 생성
    @RequestMapping(value="",method = RequestMethod.POST)
    public ResponseEntity<NewsDto> createNews(@RequestBody final NewsDto newsDto) throws IOException {
    NewsDto savedNewsDto = newsService.createNews(newsDto);
    return new ResponseEntity<>(savedNewsDto, HttpStatus.OK);
    }
    */


    /*
    @RequestMapping(value="",method= RequestMethod.GET)
    public ResponseEntity<List<NewsDto>>
    getNewsList(@RequestParam(value ="keyword", required = false,defaultValue ="" )final String keyword,
                @RequestParam(value = "sort", required = false,defaultValue = "byDate") final String sort){
        List<NewsDto> newsDtos = newsService.getNewsList(keyword, sort);
        return new ResponseEntity<>(newsDtos, HttpStatus.OK);
    }

     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getNewsList(Model model,
                              @RequestParam(value = "keyword", required = false, defaultValue = "") final String keyword,
                              @RequestParam(value = "sort", required = false, defaultValue = "byDate") final String sort) {
        List<NewsDto> newsDtos = newsService.getNewsList(keyword, sort);
        // 로그에 출력
        System.out.println("NewsDtos: " + newsDtos);
        model.addAttribute("newsList", newsDtos);
        return "list";
    }


    //뉴스 삭제
    @RequestMapping(value="/{newsId}", method = RequestMethod.DELETE)
    public ResponseEntity <NewsDto> deleteNews(@PathVariable("newsId") final long newsId){
        NewsDto deleteNewsDto = newsService.deleteNews(newsId);
        return new ResponseEntity<>(deleteNewsDto, HttpStatus.OK);
    }
}
