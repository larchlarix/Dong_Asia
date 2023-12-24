package com.example.DongAisa.controller;

import com.example.DongAisa.FormData;
import com.example.DongAisa.service.NewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class NewController {

    private final NewService newService;

    @Autowired
    public NewController(NewService newService) {
        this.newService = newService;
    }

    @GetMapping("/showForm")
    public String showForm() {
        return "new"; // HTML 폼을 보여주는 페이지로 이동
    }


@PostMapping("/processForm")
public ResponseEntity<String> processForm(@RequestBody FormData formData) {
    return newService.sendDataToFlask(formData);
}
/*
    @PostMapping(value = "/processForm", consumes = {"application/json"}, produces = {"application/json"})
    @ResponseBody
    public String processForm(@RequestBody String formData) {
        // formData를 서비스로 전달하고, 서비스에서 처리한 결과를 반환받습니다.
        String result = newService.processFormData(formData);

        // 결과를 반환합니다.
        return result;
    }

 */
}