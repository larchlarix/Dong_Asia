package com.example.DongAisa.controller;


import com.example.DongAisa.domain.News;
import com.example.DongAisa.dto.BookMarkDto;
import com.example.DongAisa.dto.NewsDto;
import com.example.DongAisa.dto.TranslationDto;
import com.example.DongAisa.repository.NewsRepository;
import com.example.DongAisa.service.BookMarkService;
import com.example.DongAisa.service.CustomUserDetails;
import com.example.DongAisa.service.NewsService;
import com.example.DongAisa.service.TranslateNewsService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/news")
public class NewsController {

    @Autowired
    NewsService newsService;

    @Autowired
    BookMarkService bookMarkService;

    @Autowired
    TranslateNewsService translateNewsService;

    @Autowired
    private NewsRepository newsRepository;

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
    /*

    @RequestMapping(value = "/{newsId}", method = RequestMethod.GET)
    public String getNews(Model model, @PathVariable("newsId") final long newsId) {
        try {
            NewsDto news = newsService.getNews(newsId);
            model.addAttribute("news", news);

            // 현재 로그인한 사용자의 userId 가져오기
            Long currentUserId = getCurrentUserId();
            model.addAttribute("currentUserId", currentUserId);

            return "main_text";
        } catch (EntityNotFoundException e) {
            // 뉴스가 존재하지 않는 경우
            return String.valueOf(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            // 다른 예외 발생 시
            return String.valueOf(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

     */
    @RequestMapping(value = "/{newsId}", method = RequestMethod.GET)
    public String getNews(Model model, @PathVariable("newsId") final long newsId) {
        try {
            NewsDto news = newsService.getNews(newsId);

            // 현재 로그인한 사용자의 userId 가져오기
            Long currentUserId = getCurrentUserId();
            model.addAttribute("currentUserId", currentUserId);

            // 북마크 상태를 모델에 추가
            boolean bookmarked = isNewsBookmarked(currentUserId,newsId);
            model.addAttribute("bookmarked", bookmarked);

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
    private boolean isNewsBookmarked(Long userId, Long newsId) {
        try {
            List<BookMarkDto> bookmarks = bookMarkService.getBookmarksByUserId(userId);

            // 주어진 newsId가 북마크 목록에 있는지 확인
            return bookmarks.stream().anyMatch(bookmark -> bookmark.getNewsId().equals(newsId));
        } catch (EntityNotFoundException e) {
            // 사용자를 찾을 수 없거나 다른 예외를 처리합니다.
            return false;
        }
    }
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            return ((CustomUserDetails) authentication.getPrincipal()).getUserId();
        }
        return null;
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
            // 현재 로그인한 사용자의 userId 가져오기
            Long currentUserId = getCurrentUserId();
            boolean bookmarked = isNewsBookmarked(currentUserId,newsId);
            model.addAttribute("bookmarked", bookmarked);

            model.addAttribute("translatedNews", translationDto);
            model.addAttribute("translatedTitle", translationDto.getTranslatedTitle());
            model.addAttribute("translatedContent", translationDto.getTranslatedContent());


            return "main_text"; // 여기에 반환할 페이지의 이름을 넣어주세요
        } catch (RuntimeException e) {
            model.addAttribute("translatedNews", new TranslationDto("번역 중 오류가 발생했습니다.", ""));

            // 현재 로그인한 사용자의 userId 가져오기
            Long currentUserId = getCurrentUserId();
            // 북마크 상태를 모델에 추가
            boolean bookmarked = isNewsBookmarked(currentUserId,newsId);
            model.addAttribute("bookmarked", bookmarked);

            return "main_text"; // 예외 발생 시에도 같은 페이지를 반환하도록 처리
        }
    }

    //뉴스리스트(카테고리, 언론사)
    /*
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
*/
    @GetMapping("/filter")
    public String getFilteredNews(
            @RequestParam(value = "category", required = false) List<Long> categories,
            @RequestParam(value = "publisher", required = false) List<Long> publishers,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            Model model

    ) {
        // 필터링된 뉴스 페이징 처리
        Pageable pageable = PageRequest.of(page, size);
        Page<News> filteredNewsPage = newsService.getFilteredNews(categories, publishers, pageable);

        model.addAttribute("isFiltering", true);
        model.addAttribute("newsFilterList", filteredNewsPage.getContent());
        model.addAttribute("currentPage", filteredNewsPage.getNumber());
        model.addAttribute("totalPages", filteredNewsPage.getTotalPages());
        model.addAttribute("totalItems", filteredNewsPage.getTotalElements());
        model.addAttribute("pageNum", page);
        model.addAttribute("size", size);
        model.addAttribute("categories", categories);
        model.addAttribute("publishers", publishers);



        return "list";
    }



/*
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
*/
//뉴스 리스트
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getNewsList(Model model,
                              @RequestParam(value = "keyword", required = false, defaultValue = "") final String keyword,
                              @RequestParam(value = "sort", required = false, defaultValue = "byDate") final String sort,
                              @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                              @RequestParam(value = "size", required = false, defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<NewsDto> newsPage = newsService.getNewsList(keyword, sort, pageable);

        boolean isFiltering = !keyword.isEmpty(); // Filtering check

        model.addAttribute("newsList", newsPage.getContent());
        model.addAttribute("isFiltering", isFiltering);
        model.addAttribute("currentPage", newsPage.getNumber());
        model.addAttribute("totalPages", newsPage.getTotalPages());
        model.addAttribute("totalItems", newsPage.getTotalElements());

        model.addAttribute("pageNum", page);
        model.addAttribute("size", size);

        return "list";
    }




    //뉴스 삭제
    @RequestMapping(value="/{newsId}", method = RequestMethod.DELETE)
    public ResponseEntity <NewsDto> deleteNews(@PathVariable("newsId") final long newsId){
        NewsDto deleteNewsDto = newsService.deleteNews(newsId);
        return new ResponseEntity<>(deleteNewsDto, HttpStatus.OK);
    }

//검색 후 뉴스 리스트
@RequestMapping(value = "/search", method = RequestMethod.GET)
public String search(@RequestParam(value = "keyword") String keyword, Model model,@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                     @RequestParam(value = "size", required = false, defaultValue = "10") int size){
    // 로그 추가
    System.out.println("Search keyword: " + keyword);

    Pageable pageable = PageRequest.of(page, size);
    Page<NewsDto> newsDtoPage = newsService.searchNews(keyword, pageable);
    List<NewsDto> newsDtoList = newsDtoPage.getContent();

    model.addAttribute("newsList", newsDtoList);

    model.addAttribute("currentPage", newsDtoPage.getNumber());
    model.addAttribute("totalPages", newsDtoPage.getTotalPages());
    model.addAttribute("totalItems", newsDtoPage.getTotalElements());

    model.addAttribute("pageNum", page);
    model.addAttribute("size", size);
    model.addAttribute("keyword", keyword);

    boolean isFiltering = false;
    model.addAttribute("isFiltering", isFiltering);

    return "searchList";
}



}
