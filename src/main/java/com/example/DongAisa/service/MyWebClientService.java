package com.example.DongAisa.service;

// MyWebClient.java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class MyWebClientService {

    private static final Logger logger = LoggerFactory.getLogger(MyWebClientService.class);

    private final WebClient webClient;

    public MyWebClientService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:5000").build();
    }

    // Flask 서버로 데이터 전송
    public Mono<String> sendDataToFlask(String requestData) {
        String endpoint = "/send_data_to_flask";

        // 데이터를 Map으로 감싸서 필요한 필드를 추가할 수 있도록 함
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("requestData", requestData);

        return webClient.post()
                .uri(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dataMap)  // Map을 JSON으로 변환하여 전송
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> handleFlaskResponse(response))
                .doOnError(throwable -> logger.error("Error communicating with Flask server", throwable))
                .onErrorResume(throwable -> Mono.just("Error communicating with Flask server"));
    }

    // Flask 서버로부터 데이터 수신
    public Mono<String> fetchDataFromFlask() {
        String endpoint = "/receive_data_from_flask";

        return webClient.post()
                .uri(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> logger.info("Response from Flask server: {}", response))
                .doOnError(throwable -> logger.error("Error fetching data from Flask server", throwable))
                .onErrorResume(throwable -> Mono.just("Error fetching data from Flask server"));
    }

    private void handleFlaskResponse(String response) {
        logger.info("Response from Flask server: {}", response);
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
                .bodyToMono(Map.class)
                .flatMap(response -> {
                    if (response.containsKey("image")) {
                        String imageData = (String) response.get("image");
                        return Mono.justOrEmpty(validateBase64AndDecode(imageData));
                    } else {
                        logger.error("Flask 서버 응답에서 이미지 데이터를 찾을 수 없습니다.");
                        return Mono.empty();
                    }
                })
                .doOnNext(imageData -> logger.info("Python Flask 서버에서 이미지 데이터를 수신했습니다."))
                .doOnError(throwable -> {
                    logger.error("Flask 서버에서 이미지 데이터를 가져오는 중 오류가 발생했습니다.", throwable);
                    // Image Not Found 경우에 대한 로그 추가
                    if (throwable instanceof IllegalArgumentException && "Invalid Base64 data received from Flask server".equals(throwable.getMessage())) {
                        logger.error("Image Not Found: Invalid Base64 data received from Flask server");
                    }
                })
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

