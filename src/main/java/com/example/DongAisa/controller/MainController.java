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
import java.util.Arrays;
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

    @Autowired
    TranslateTitleService translateTitleService;
    private final TopKeywordService topKeywordService;

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    public MainController(TopKeywordService topKeywordService) {
        this.topKeywordService = topKeywordService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getNewsList(Model model,
                              @RequestParam(value = "keyword", required = false, defaultValue = "") final String keyword,
                              @RequestParam(value = "sort", required = false, defaultValue = "byDate") final String sort
    ) {
        List<NewsDto> newsDtos = newsService.getNewsList(keyword, sort);
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
@PostMapping
public String getTranslatedSentence(@RequestParam(required = false) String sentence,
                                    @RequestParam(required = false) String title,
                                    Model model) {
    try {
        if (sentence != null) {
            String translatedSentence = translateService.getTranslatedSentence(sentence);
            model.addAttribute("translatedSentence", translatedSentence);
        }

        if (title != null) {
            String translatedTitle = translateTitleService.getTranslatedTitle(title);
            model.addAttribute("translatedTitle", translatedTitle);
        }
    } catch (RuntimeException e) {
        model.addAttribute("translatedSentence", "검색 번역 중 오류가 발생했습니다.");
        model.addAttribute("translatedTitle", "제목 번역 중 오류가 발생했습니다.");
    }

    // 이 부분은 필요에 따라 뷰 이름을 조정할 수 있습니다.
    return "main";
}

 */

    @PostMapping
    public String getTranslatedSentence(@RequestParam(required = false) String sentence,

                                        Model model) {
        try {
            if (sentence != null) {
                String translatedSentence = translateService.getTranslatedSentence(sentence);
                model.addAttribute("translatedSentence", translatedSentence);
            }


        } catch (RuntimeException e) {
            model.addAttribute("translatedSentence", "검색 번역 중 오류가 발생했습니다.");

        }

        // 이 부분은 필요에 따라 뷰 이름을 조정할 수 있습니다.
        return "main";
    }

    /*
    @PostMapping("/translatedtitles")
    public ModelAndView getTranslatedtitles( @RequestParam(required = false) String title,
                                             Model model,
                                             @RequestParam(value = "keyword", required = false, defaultValue = "") final String keyword,
                                             @RequestParam(value = "sort", required = false, defaultValue = "byDate") final String sort){
        ModelAndView modelAndView = new ModelAndView("main");
        List<NewsDto> newsDtos = newsService.getNewsList(keyword, sort);

        model.addAttribute("newsList", newsDtos);
        try {

            if (title != null) {
                List<String> originalTitles = Arrays.asList(title.split(","));
                List<String> translatedTitles = translateTitleService.getTranslatedTitles(originalTitles);
                modelAndView.addObject("translatedTitles", translatedTitles);
                // 로깅 추가
                logger.info("Original Titles: " + originalTitles);
                logger.info("Translated Titles: " + translatedTitles);
            }
        } catch (RuntimeException e) {

            model.addAttribute("translatedTitles", Collections.emptyList());
            // 오류 로깅 추가
            logger.error("Error during translation", e);
        }

        return modelAndView;

    }*/

/*
    @PostMapping("/translatedtitles")
    public String getTranslatedtitles(
            @RequestParam(value = "originalTitles", required = false) String[] originalTitles,
            @RequestParam(value = "keyword", required = false, defaultValue = "") final String keyword,
            @RequestParam(value = "sort", required = false, defaultValue = "byDate") final String sort,
            Model model
    ) {
        try {
            if (originalTitles != null && originalTitles.length > 0) {
                TranslationTitleDto translationTitleDto = translateTitleService.getTranslatedTitles(originalTitles);
                // 로그 추가
                logger.info("번역된 제목: " + translationTitleDto);
                model.addAttribute("translatedTitles", translationTitleDto.getTitles());
            }

            return "main";
        } catch (RuntimeException e) {
            // 오류 로깅 추가
            logger.error("번역 중 오류 발생", e);
            return "main";
        }

    }

 */

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

