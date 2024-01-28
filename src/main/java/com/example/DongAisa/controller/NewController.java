package com.example.DongAisa.controller;

import com.example.DongAisa.dto.FormData;
import com.example.DongAisa.dto.KeywordData;
import com.example.DongAisa.dto.NewsData;
import com.example.DongAisa.dto.NewsDto;
import com.example.DongAisa.service.Keyword_suggestedService;
import com.example.DongAisa.service.NewService;
import com.example.DongAisa.service.NewsService;
import com.example.DongAisa.service.TranslateService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Controller
public class NewController {

    private final NewService newService;

    @Autowired
    NewsService newsService;

    @Autowired
    TranslateService translateService;

    private final Keyword_suggestedService keyword_suggestedService;

    @Autowired
    public NewController(NewService newService, Keyword_suggestedService keyword_suggestedService) {
        this.newService = newService;
        this.keyword_suggestedService = keyword_suggestedService;
    }




@PostMapping("/processForm")
public ResponseEntity<String> processForm(@RequestBody FormData formData) {
    return newService.sendDataToFlask(formData);
}

//분석페이지
    //기간, 신문사
    @GetMapping("/analysis")
    public String analysis() {
        return "analysis_test"; // HTML 폼을 보여주는 페이지로 이동
    }
    @PostMapping(value = "/analysis", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getTranslatedAnalysis(@RequestBody Map<String, String> requestData) {
        try {
            String graphText = requestData.get("graphText");
            String translatedGraph = translateService.getTranslatedAnalysis(graphText);
            return translatedGraph;
        } catch (RuntimeException e) {
            return "번역 중 오류가 발생했습니다.";
        }

    }

    @PostMapping("/process")
    public ResponseEntity<String> process(@RequestBody NewsData newsData) {
        return newService.sendData(newsData);
    }


    @GetMapping("/keyword_analysis")
    public String keyanalysis(Model model){
        // Flask에서 받은 JSON 데이터를 변환하여 모델에 추가
        String responseData = keyword_suggestedService.keyword_suggested();
        List<String> keywordSuggestedList = convertJsonToList(responseData);
        model.addAttribute("keyword_suggested", keywordSuggestedList);

        return "analysis_keyword";}

    @GetMapping("/get_news_detail")
    public ResponseEntity<NewsDto> getNewsDetail(@RequestParam("newsId") Long newsId) {
        NewsDto newsDetail = newsService.getNewsDetail(newsId);

        if (newsDetail != null) {
            return ResponseEntity.ok(newsDetail);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/keyword_process")
    public ResponseEntity<String> keyword_process(@RequestBody KeywordData keywordData) {
        return newService.sendKeyData(keywordData);
    }
    @PostMapping("/keyword_amount_process")
    public ResponseEntity<String> keyword_amount_process(@RequestBody KeywordData keywordData) {
        // 두 번째 엔드포인트에 대한 로직 추가
        return newService.sendAmountData(keywordData);
    }
    @PostMapping("/keyword_posinega")
    public ResponseEntity<String> keyword_posinega(@RequestBody KeywordData keywordData) {
        // 두 번째 엔드포인트에 대한 로직 추가
        return newService.sendPosinegaData(keywordData);
    }


    @PostMapping("/keyword_related_news")
    public ResponseEntity<String> keyword_related_news(@RequestBody KeywordData keywordData) {
        // 두 번째 엔드포인트에 대한 로직 추가
        return newService.sendRelatedNewsData(keywordData);
    }




    private List<String> convertJsonToList(String jsonData) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // JSON을 List<String>으로 변환
            return objectMapper.readValue(jsonData, new TypeReference<List<String>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            // 예외 처리: JSON 변환 실패
            return Collections.emptyList(); // 빈 List 반환 또는 다른 처리 방법 선택
        }
    }

    /*
    @RequestMapping(value = "/get_related_news", method = RequestMethod.GET)
    @ResponseBody
    public List<NewsDto> getRelatedNews(@RequestParam(value = "newsId") Long newsId) {
        // newsId를 이용하여 관련된 뉴스 목록을 가져옴
       // List<NewsDto> relatedNews = newsService.getNews(newsId);

        return relatedNews;
    }

/*
    @PostMapping(value = "/processForm", consumes = {"application/json"}, produces = {"application/json"})
    @ResponseBody
    public String processForm(@RequestBody String formData) {
        // formData를 서비스로 전달하고, 서비스에서 처리한 결과를 반환받습니다.
        String result = newService.processFormData(formData);

        // 결과를 반환합니다.
        return result;
    }

 */
}