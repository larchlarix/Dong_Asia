package com.example.DongAisa.controller;

import com.example.DongAisa.dto.NewsDto;
import com.example.DongAisa.service.NewsService;
import com.example.DongAisa.service.TopKeywordService;
import com.example.DongAisa.service.TranslateService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    NewsService newsService;

    @Autowired
    TranslateService translateService;
    private final TopKeywordService topKeywordService;

    @Autowired
    public MainController(TopKeywordService topKeywordService) {
        this.topKeywordService = topKeywordService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getNewsList(Model model,
                              @RequestParam(value = "keyword", required = false, defaultValue = "") final String keyword,
                              @RequestParam(value = "sort", required = false, defaultValue = "byDate") final String sort) {
        List<NewsDto> newsDtos = newsService.getNewsList(keyword, sort);
        // 로그에 출력
        System.out.println("NewsDtos: " + newsDtos);
        model.addAttribute("newsList", newsDtos);

        // Flask에서 받은 JSON 데이터를 변환하여 모델에 추가
        String responseData = topKeywordService.getFlaskData();
        Map<String, Object> topKeywordData = convertJsonToMap(responseData);
        //TopKeywordData topKeywordData = convertJsonToTopKeywordData(responseData);
        model.addAttribute("topKeywordData", topKeywordData);
        return "main";
    }

    private Map<String, Object> convertJsonToMap(String jsonData) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // JSON을 Map으로 변환
            return objectMapper.readValue(jsonData, new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            // 예외 처리: JSON 변환 실패
            return Collections.emptyMap(); // 빈 Map 반환 또는 다른 처리 방법 선택
        }
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
