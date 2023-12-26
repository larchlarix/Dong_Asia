package com.example.DongAisa.controller;

import com.example.DongAisa.dto.FormData;
import com.example.DongAisa.dto.KeywordData;
import com.example.DongAisa.dto.NewsData;
import com.example.DongAisa.service.NewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class NewController {

    private final NewService newService;

    @Autowired
    public NewController(NewService newService) {
        this.newService = newService;
    }

    @GetMapping("/showForm")
    public String showForm() {
        return "new"; // HTML 폼을 보여주는 페이지로 이동
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

    @PostMapping("/process")
    public ResponseEntity<String> process(@RequestBody NewsData newsData) {
        return newService.sendData(newsData);
    }


    @GetMapping("/keyword_analysis")
    public String keyanalysis(){ return "analysis_keyword";}

    @PostMapping("/keyword_process")
    public ResponseEntity<String> keyword_process(@RequestBody KeywordData keywordData) {
        return newService.sendKeyData(keywordData);
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