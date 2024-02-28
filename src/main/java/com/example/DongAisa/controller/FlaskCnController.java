package com.example.DongAisa.controller;


import com.example.DongAisa.dto.FormData;
import com.example.DongAisa.dto.KeywordData;
import com.example.DongAisa.dto.NewsData;
import com.example.DongAisa.dto.NewsDto;
import com.example.DongAisa.service.FlaskCnService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public class FlaskCnController {

    private final FlaskCnService flaskCnService;

    @Autowired
    public FlaskCnController(FlaskCnService flaskCnService) {
        this.flaskCnService = flaskCnService;

    }

    @PostMapping("/processForm_cn")
    public ResponseEntity<String> processForm(@RequestBody FormData formData) {
        return flaskCnService.sendDataToFlask(formData);
    }

    //분석페이지
    //기간, 신문사
    @GetMapping("/analysis_cn")
    public String analysis() {
        return "analysis_amount_cn"; // HTML 폼을 보여주는 페이지로 이동
    }

    /*
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

     */

    @PostMapping("/process_cn")
    public ResponseEntity<String> process(@RequestBody NewsData newsData) {
        return flaskCnService.sendData(newsData);
    }


    @GetMapping("/keyword_analysis_cn")
    public String keyanalysis(Model model){
        // Flask에서 받은 JSON 데이터를 변환하여 모델에 추가
        String responseData = flaskCnService.keyword_suggested();
        List<String> keywordSuggestedList = convertJsonToList(responseData);
        model.addAttribute("keyword_suggested", keywordSuggestedList);

        return "analysis_keyword_cn";}
/*
    @GetMapping("/get_news_detail")
    public ResponseEntity<NewsDto> getNewsDetail(@RequestParam("newsId") Long newsId) {
        NewsDto newsDetail = newsService.getNewsDetail(newsId);

        if (newsDetail != null) {
            return ResponseEntity.ok(newsDetail);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

 */

    @PostMapping("/keyword_process_cn")
    public ResponseEntity<String> keyword_process(@RequestBody KeywordData keywordData) {
        return flaskCnService.sendKeyData(keywordData);
    }
    @PostMapping("/keyword_amount_process_cn")
    public ResponseEntity<String> keyword_amount_process(@RequestBody KeywordData keywordData) {
        // 두 번째 엔드포인트에 대한 로직 추가
        return flaskCnService.sendAmountData(keywordData);
    }
    @PostMapping("/keyword_posinega_cn")
    public ResponseEntity<String> keyword_posinega(@RequestBody KeywordData keywordData) {
        // 두 번째 엔드포인트에 대한 로직 추가
        return flaskCnService.sendPosinegaData(keywordData);
    }


    @PostMapping("/keyword_related_news_cn")
    public ResponseEntity<String> keyword_related_news(@RequestBody KeywordData keywordData) {
        // 두 번째 엔드포인트에 대한 로직 추가
        return flaskCnService.sendRelatedNewsData(keywordData);
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
}
