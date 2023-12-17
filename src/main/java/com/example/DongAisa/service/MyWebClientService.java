package com.example.DongAisa.service;

// MyWebClient.java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MyWebClientService {

    private static final Logger logger = LoggerFactory.getLogger(MyWebClientService.class);

    private final WebClient webClient;

    public MyWebClientService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:5000").build();
    }

    public Mono<String> fetchDataFromFlaskServer() {
        String endpoint = "/receive_data";

        return webClient.get()
                .uri(endpoint)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> logger.info("Response from Python Flask server: {}", response))
                .onErrorResume(throwable -> {
                    logger.error("Error fetching data from Flask server", throwable);
                    return Mono.empty();
                });
    }

    // Flask 서버로부터 이미지 데이터를 비동기적으로 가져오는 메서드
    public Mono<String> fetchImageDataFromFlaskServer() {
        // WebClient를 사용하여 Flask 서버에 GET 요청을 보냄
        return webClient.get()
                .uri("/receive_image")
                .retrieve()
                .bodyToMono(String.class);
    }
}

