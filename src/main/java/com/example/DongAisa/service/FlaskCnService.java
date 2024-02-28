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
public class FlaskCnService {

    @Value("http://localhost:5050")
    private String flaskUrlCn;

    private final RestTemplate restTemplate;

    @Autowired
    public FlaskCnService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> sendDataToFlask(FormData formData) {


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<FormData> requestEntity = new HttpEntity<>(formData, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(flaskUrlCn+"/processData_cn", requestEntity, String.class);

        return ResponseEntity.ok(responseEntity.getBody());
    }

    public ResponseEntity<String> sendData(NewsData newsData) {


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<NewsData> requestEntity = new HttpEntity<>(newsData, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(flaskUrlCn+"/process_cn", requestEntity, String.class);

        return ResponseEntity.ok(responseEntity.getBody());
    }

    public ResponseEntity<String> sendKeyData(KeywordData keywordData) {


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<KeywordData> requestEntity = new HttpEntity<>(keywordData, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(flaskUrlCn+"/keyword_process_cn", requestEntity, String.class);

        return ResponseEntity.ok(responseEntity.getBody());
    }

    public ResponseEntity<String> sendAmountData(KeywordData keywordData) {


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<KeywordData> requestEntity = new HttpEntity<>(keywordData, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(flaskUrlCn+"/keyword_amount_process_cn", requestEntity, String.class);

        return ResponseEntity.ok(responseEntity.getBody());
    }
    public ResponseEntity<String> sendPosinegaData(KeywordData keywordData) {


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<KeywordData> requestEntity = new HttpEntity<>(keywordData, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(flaskUrlCn+"/keyword_posinega_cn", requestEntity, String.class);

        return ResponseEntity.ok(responseEntity.getBody());
    }

    public ResponseEntity<String> sendRelatedNewsData(KeywordData keywordData) {


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<KeywordData> requestEntity = new HttpEntity<>(keywordData, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(flaskUrlCn+"/keyword_related_news_cn", requestEntity, String.class);

        return ResponseEntity.ok(responseEntity.getBody());
    }

    public String keyword_suggested() {
        return restTemplate.getForObject(flaskUrlCn+"/keyword_suggested_cn", String.class);
    }

    public String getFlaskData() {
        return restTemplate.getForObject(flaskUrlCn+"/topKeywordData_cn", String.class);
    }

}
