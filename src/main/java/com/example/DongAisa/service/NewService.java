package com.example.DongAisa.service;

import com.example.DongAisa.FormData;
import com.example.DongAisa.NewsData;
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
/*
    public String processFormData(String formData) {
        // 받은 데이터를 Flask 서버에 전송
        String response = restTemplate.postForObject(flaskUrl + "/your-endpoint", formData, String.class);

        // 받은 응답을 그대로 반환
        return response;
    }

 */
}