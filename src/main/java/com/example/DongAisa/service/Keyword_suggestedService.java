package com.example.DongAisa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Keyword_suggestedService {
    @Value("${flask.url}")
    private String flaskUrl;

    private final RestTemplate restTemplate;

    @Autowired
    public Keyword_suggestedService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String keyword_suggested() {
        return restTemplate.getForObject(flaskUrl+"/keyword_suggested", String.class);
    }
}
