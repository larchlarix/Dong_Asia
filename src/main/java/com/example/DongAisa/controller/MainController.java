package com.example.DongAisa.controller;

import com.example.DongAisa.dto.NewsDto;
import com.example.DongAisa.service.NewsService;
import com.example.DongAisa.service.TranslateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    NewsService newsService;

    @Autowired
    TranslateService translateService;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getNewsList(Model model,
                              @RequestParam(value = "keyword", required = false, defaultValue = "") final String keyword,
                              @RequestParam(value = "sort", required = false, defaultValue = "byDate") final String sort) {
        List<NewsDto> newsDtos = newsService.getNewsList(keyword, sort);
        // 로그에 출력
        System.out.println("NewsDtos: " + newsDtos);
        model.addAttribute("newsList", newsDtos);
        return "main";
    }
    @PostMapping
    public String getTranslatedSentence(@RequestParam String sentence, Model model) {
        try {
            String translatedSentence = translateService.getTranslatedSentence(sentence);
            model.addAttribute("translatedSentence", translatedSentence);
        } catch (RuntimeException e) {
            model.addAttribute("translatedSentence", "번역 중 오류가 발생했습니다.");
        }
        // 이 부분은 필요에 따라 뷰 이름을 조정할 수 있습니다.
        return "main";
    }
}
