package com.example.DongAisa.controller;

import com.example.DongAisa.service.MyWebClientService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class MyController {

    private final MyWebClientService webClientService;

    private static final Logger logger = LoggerFactory.getLogger(MyController.class);

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
                .map(imageData -> {
                    String base64Image = Base64.getEncoder().encodeToString(imageData);
                    model.addAttribute("base64Image", base64Image);
                    return "imageView"; // Assuming "imageView" is the name of your view template
                })
                .doOnSuccess(response -> logger.info("Successfully retrieved and displayed the image"))
                .defaultIfEmpty("imageNotFound");
    }

    /*
    @GetMapping("/showImageData")
    public Mono<ResponseEntity<ByteArrayResource>> showImage(Model model) {
        return webClientService.fetchImageDataFromFlaskServer()
                .map(imageData -> new ByteArrayResource(imageData))
                .map(resource -> ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(resource))
                .doOnSuccess(response -> logger.info("Successfully retrieved and displayed the image"))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
*/

    private static void handleResponse(String response) {
        System.out.println("Received response in handleResponse: " + response);
    }
}





