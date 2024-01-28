package com.example.DongAisa.service;

import com.example.DongAisa.dto.FormData;
import com.example.DongAisa.dto.KeywordData;
import com.example.DongAisa.dto.NewsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
@Service
public class NewService {

    @Value("${flask.url}")
    private String flaskUrl;

    private final RestTemplate restTemplate;

    @Autowired
    public NewService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> sendDataToFlask(FormData formData) {


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<FormData> requestEntity = new HttpEntity<>(formData, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(flaskUrl+"/processData", requestEntity, String.class);

        return ResponseEntity.ok(responseEntity.getBody());
    }

    public ResponseEntity<String> sendData(NewsData newsData) {


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<NewsData> requestEntity = new HttpEntity<>(newsData, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(flaskUrl+"/process", requestEntity, String.class);

        return ResponseEntity.ok(responseEntity.getBody());
    }

    public ResponseEntity<String> sendKeyData(KeywordData keywordData) {


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<KeywordData> requestEntity = new HttpEntity<>(keywordData, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(flaskUrl+"/keyword_process", requestEntity, String.class);

        return ResponseEntity.ok(responseEntity.getBody());
    }

    public ResponseEntity<String> sendAmountData(KeywordData keywordData) {


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<KeywordData> requestEntity = new HttpEntity<>(keywordData, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(flaskUrl+"/keyword_amount_process", requestEntity, String.class);

        return ResponseEntity.ok(responseEntity.getBody());
    }
    public ResponseEntity<String> sendPosinegaData(KeywordData keywordData) {


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<KeywordData> requestEntity = new HttpEntity<>(keywordData, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(flaskUrl+"/keyword_posinega", requestEntity, String.class);

        return ResponseEntity.ok(responseEntity.getBody());
    }

    public ResponseEntity<String> sendRelatedNewsData(KeywordData keywordData) {


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<KeywordData> requestEntity = new HttpEntity<>(keywordData, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(flaskUrl+"/keyword_related_news", requestEntity, String.class);

        return ResponseEntity.ok(responseEntity.getBody());
    }
/*
    public String processFormData(String formData) {
        // 받은 데이터를 Flask 서버에 전송
        String response = restTemplate.postForObject(flaskUrl + "/your-endpoint", formData, String.class);

        // 받은 응답을 그대로 반환
        return response;
    }

 */
}