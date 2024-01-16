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
    /*
    @PostMapping("/translate")
    public String getTranslatedNews(
            @RequestParam String title,
            @RequestParam String content,
            Model model
    ) {
        try {
            TranslationDto translationDto = translateNewsService.getTranslatedNews(title, content);
            model.addAttribute("translatedNews", translationDto);
            return "main_text"; // 여기에 반환할 페이지의 이름을 넣어주세요
        } catch (RuntimeException e) {
            model.addAttribute("translatedNews", new TranslationDto("번역 중 오류가 발생했습니다.", ""));
            return "main_text"; // 예외 발생 시에도 같은 페이지를 반환하도록 처리
        }
    }

*/


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
/*
    //뉴스 검색
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(@RequestParam(value = "keyword") String keyword, Model model){
        List<NewsDto> newsDtoList = newsService.searchNews(keyword);
        model.addAttribute("newsList",newsDtoList);
        return "list";
    }

 */
@RequestMapping(value = "/search", method = RequestMethod.GET)
public String search(@RequestParam(value = "keyword") String keyword, Model model){
    // 로그 추가
    System.out.println("Search keyword: " + keyword);
    List<NewsDto> newsDtoList = newsService.searchNews(keyword);
    model.addAttribute("newsList", newsDtoList);
    return "list";
}



}
