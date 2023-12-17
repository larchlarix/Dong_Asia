package com.example.DongAisa.controller;

import com.example.DongAisa.service.MyWebClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import reactor.core.publisher.Mono;

import java.util.Base64;

@Controller
public class MyController {

    private final MyWebClientService webClientService;

    public MyController(MyWebClientService webClientService) {
        this.webClientService = webClientService;
    }

    @GetMapping("/showJsonData")
    public Mono<String> showJsonData(Model model) {
        return webClientService.fetchDataFromFlaskServer()
                .doOnNext(response -> handleResponse(response))
                .doOnNext(response -> model.addAttribute("jsonData", response))
                .thenReturn("jsonView");
    }

    @GetMapping("/showImageData")
    public Mono<String> showImageData(Model model) {
        return webClientService.fetchImageDataFromFlaskServer()
                .doOnNext(response -> handleImageResponse(response, model))
                .thenReturn("imageView");
    }

    private static void handleImageResponse(String response, Model model) {
        System.out.println("Received response in handleImageResponse: " + response);

        try {
            // 이미지를 Base64에서 디코딩
            byte[] imageBytes = Base64.getDecoder().decode(response);

            // 디코딩된 이미지 데이터를 모델에 추가하여 템플릿에서 사용할 수 있도록 함
            model.addAttribute("imageData", imageBytes);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            // 추가 로깅 또는 오류 처리를 수행하세요.
        }
    }

    private static void handleResponse(String response) {
        System.out.println("Received response in handleResponse: " + response);
    }
}


