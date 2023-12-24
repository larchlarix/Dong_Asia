package com.example.DongAisa.controller;

import com.example.DongAisa.service.NewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class NewController {

    private final NewService newService;

    @Autowired
    public NewController(NewService newService) {
        this.newService = newService;
    }

    @GetMapping("/new_f")
    public ResponseEntity<?> handleFormData(@RequestParam String id, @RequestParam String password, @RequestParam String email) {
        try {
            // 받은 데이터를 그대로 서비스로 전달
            String result = newService.processFormData(id, password, email);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("데이터 처리 중 오류가 발생했습니다", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}