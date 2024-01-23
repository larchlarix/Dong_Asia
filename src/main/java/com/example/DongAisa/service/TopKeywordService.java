package com.example.DongAisa.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TopKeywordService {

    @Value("${flask.url}")
    private String flaskUrl;

    private final RestTemplate restTemplate;

    @Autowired
    public TopKeywordService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getFlaskData() {
        return restTemplate.getForObject(flaskUrl+"/topKeywordData", String.class);
    }
}
