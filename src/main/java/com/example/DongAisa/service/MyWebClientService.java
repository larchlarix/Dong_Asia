package com.example.DongAisa.service;

// MyWebClient.java
import com.example.DongAisa.ImageDataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.Base64;

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


    public Mono<byte[]> fetchImageDataFromFlaskServer() {
        String endpoint = "/receive_image";

        return webClient.get()
                .uri(endpoint)
                .retrieve()
                .bodyToMono(String.class)
                .map(this::validateBase64AndDecode)
                .doOnNext(imageData -> logger.info("Received image data from Python Flask server"))
                .doOnError(throwable -> logger.error("Error fetching image data from Flask server", throwable))
                .onErrorResume(throwable -> Mono.empty());
    }

    private byte[] validateBase64AndDecode(String base64Data) {
        // 패딩 추가
        int padding = base64Data.endsWith("==") ? 2 : (base64Data.endsWith("=") ? 1 : 0);
        base64Data = base64Data + "=".repeat(padding);

        // 유효성 검사 추가
        if (!isValidBase64(base64Data)) {
            logger.error("Invalid Base64 data received from Flask server: {}", base64Data);
            throw new IllegalArgumentException("Invalid Base64 data received from Flask server");
        }

        try {
            return Base64.getDecoder().decode(base64Data);
        } catch (IllegalArgumentException e) {
            logger.error("Error decoding Base64 data received from Flask server: {}", base64Data, e);
            throw e; // or handle the error as appropriate
        }
    }

    private boolean isValidBase64(String base64Data) {
        // 패턴 및 길이 검사
        return base64Data.matches("^[a-zA-Z0-9+/]*={0,2}$") && base64Data.length() % 4 == 0;
    }


}

