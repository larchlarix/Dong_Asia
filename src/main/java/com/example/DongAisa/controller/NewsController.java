package com.example.DongAisa.controller;


import com.example.DongAisa.domain.News;
import com.example.DongAisa.dto.NewsDto;
import com.example.DongAisa.dto.TranslationDto;
import com.example.DongAisa.service.NewsService;
import com.example.DongAisa.service.TranslateNewsService;
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

    @Autowired
    TranslateNewsService translateNewsService;

    // 생성자 주입을 통해 TranslateNewsService 주입
    public NewsController(TranslateNewsService translateNewsService) {
        this.translateNewsService = translateNewsService;
    }
    /*뉴스 조회
    @RequestMapping(value="/{newsId}", method = RequestMethod.GET)
    public ResponseEntity<NewsDto> getNews(@PathVariable("newsId") final long newsId) {
        NewsDto news = newsService.getNews(newsId);
        return new ResponseEntity<>(news, HttpStatus.OK);
    }
*/

    @RequestMapping(value = "/{newsId}", method = RequestMethod.GET)
    public String getNews(Model model, @PathVariable("newsId") final long newsId) {
        try {
            NewsDto news = newsService.getNews(newsId);
            model.addAttribute("news", news);
            return "main_text";
        } catch (EntityNotFoundException e) {
            // 뉴스가 존재하지 않는 경우
            return String.valueOf(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            // 다른 예외 발생 시
            return String.valueOf(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/{newsId}")
    public String getTranslatedNews(
            @PathVariable Long newsId,
            @RequestParam String title,
            @RequestParam String content,
            Model model
    ) {
        try {
            NewsDto news = newsService.getNews(newsId);
            // 번역된 섹션을 나타내는 모델에도 원래 뉴스의 정보를 추가
            model.addAttribute("news", news);
            TranslationDto translationDto = translateNewsService.getTranslatedNews(title, content);


            model.addAttribute("translatedNews", translationDto);
            model.addAttribute("translatedTitle", translationDto.getTranslatedTitle());
            model.addAttribute("translatedContent", translationDto.getTranslatedContent());
            return "main_text"; // 여기에 반환할 페이지의 이름을 넣어주세요
        } catch (RuntimeException e) {
            model.addAttribute("translatedNews", new TranslationDto("번역 중 오류가 발생했습니다.", ""));
            return "main_text"; // 예외 발생 시에도 같은 페이지를 반환하도록 처리
        }
    }

    //뉴스리스트(카테고리, 언론사)
    @PostMapping("")
    public String getFilteredNews(
            @RequestParam(value = "category", required = false) List<Long> categories,
            @RequestParam(value = "publisher", required = false) List<Long> publishers,
            Model model
    ) {
        List<News> filteredNews = newsService.getFilteredNews(categories, publishers);
        model.addAttribute("isFiltering", true); // 추가된 부분
        model.addAttribute("newsFilterList", filteredNews);
        return "list";
    }





    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getNewsList(Model model,
                              @RequestParam(value = "keyword", required = false, defaultValue = "") final String keyword,
                              @RequestParam(value = "sort", required = false, defaultValue = "byDate") final String sort) {
        List<NewsDto> newsDtos = newsService.getNewsList(keyword, sort);
        // isFiltering 변수 추가 및 초기화
        boolean isFiltering = false;

        // 로그에 출력
        System.out.println("NewsDtos: " + newsDtos);
        model.addAttribute("newsList", newsDtos);
        model.addAttribute("isFiltering", isFiltering);
        return "list";
    }


    //뉴스 삭제
    @RequestMapping(value="/{newsId}", method = RequestMethod.DELETE)
    public ResponseEntity <NewsDto> deleteNews(@PathVariable("newsId") final long newsId){
        NewsDto deleteNewsDto = newsService.deleteNews(newsId);
        return new ResponseEntity<>(deleteNewsDto, HttpStatus.OK);
    }

@RequestMapping(value = "/search", method = RequestMethod.GET)
public String search(@RequestParam(value = "keyword") String keyword, Model model){
    // 로그 추가
    System.out.println("Search keyword: " + keyword);
    List<NewsDto> newsDtoList = newsService.searchNews(keyword);
    model.addAttribute("newsList", newsDtoList);

    boolean isFiltering = false;
    model.addAttribute("isFiltering", isFiltering);

    return "list";
}



}
