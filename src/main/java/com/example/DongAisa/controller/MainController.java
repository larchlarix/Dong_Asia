package com.example.DongAisa.controller;

import com.example.DongAisa.dto.NewsDto;
import com.example.DongAisa.dto.TranslationDto;
import com.example.DongAisa.dto.TranslationTitleDto;
import com.example.DongAisa.service.NewsService;
import com.example.DongAisa.service.TopKeywordService;
import com.example.DongAisa.service.TranslateService;
import com.example.DongAisa.service.TranslateTitleService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.*;


@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    NewsService newsService;

    @Autowired
    TranslateService translateService;

    @Autowired
    TranslateTitleService translateTitleService;
    private final TopKeywordService topKeywordService;

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    public MainController(TopKeywordService topKeywordService) {
        this.topKeywordService = topKeywordService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getMainNewsList(Model model,
                              @RequestParam(value = "keyword", required = false, defaultValue = "") final String keyword,
                              @RequestParam(value = "sort", required = false, defaultValue = "byDate") final String sort
    ) {
        List<NewsDto> newsDtos = newsService.getMainNewsList(keyword, sort);
        // 로그에 출력
        System.out.println("NewsDtos: " + newsDtos);
        model.addAttribute("newsList", newsDtos);

        // Flask에서 받은 JSON 데이터를 변환하여 모델에 추가
        String responseData = topKeywordService.getFlaskData();
        Map<String, Object> topKeywordData = convertJsonToMap(responseData);

        model.addAttribute("topKeywordData", topKeywordData);


        return "main";
    }

    private Map<String, Object> convertJsonToMap(String jsonData) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // JSON을 Map으로 변환
            return objectMapper.readValue(jsonData, new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            // 예외 처리: JSON 변환 실패
            return Collections.emptyMap(); // 빈 Map 반환 또는 다른 처리 방법 선택
        }
    }

    /*
    //번역+검색 기능 합친 것
    @PostMapping("/translateAndSearch")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> translateAndSearch(@RequestParam(required = false) String sentence) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (sentence != null) {
                // 번역
                String translatedSentence = translateService.getTranslatedSentence(sentence);
                result.put("translatedSentence", translatedSentence);

                // 검색
                List<NewsDto> newsDtoList = newsService.searchNews(translatedSentence);
                result.put("newsList", newsDtoList);
                result.put("isFiltering", true);
            }
        } catch (RuntimeException e) {
            result.put("error", "번역 및 검색 중 오류가 발생했습니다.");
        }

        return ResponseEntity.ok(result);
    }
    */

    //검색창 번역기능
    @PostMapping("/translateSubmit")
    @ResponseBody
    public ResponseEntity<Map<String, String>> translate(@RequestParam(required = false) String sentence) {
        Map<String, String> result = new HashMap<>();
        try {
            if (sentence != null) {
                String translatedSentence = translateService.getTranslatedSentence(sentence);
                result.put("translatedSentence", translatedSentence);
            }
        } catch (RuntimeException e) {
            result.put("error", "번역 중 오류가 발생했습니다.");
        }

        return ResponseEntity.ok(result);
    }


    //기사 제목 번역 기능
    @PostMapping("/translatedtitles")
    public ResponseEntity<TranslationTitleDto> getTranslatedTitles(@RequestParam(value = "originalTitles", required = false) String[] originalTitles) {
        try {
            TranslationTitleDto translationTitleDto = translateTitleService.getTranslatedTitles(originalTitles);
            return ResponseEntity.ok(translationTitleDto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}


