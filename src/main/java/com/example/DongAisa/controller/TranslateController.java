package com.example.DongAisa.controller;

import com.example.DongAisa.service.TranslateService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
/*
@Controller
@RequestMapping("/")
public class TranslateController {

    private final TranslateService translateService;

    public TranslateController(TranslateService translateService) {
        this.translateService = translateService;
    }

    @GetMapping
    public String showTranslatePage(Model model) {
        // Thymeleaf에서의 null 이슈 방지를 위해 빈 문자열을 추가
        model.addAttribute("translatedSentence", "");
        return "main";
    }

    @PostMapping
    public String getTranslatedSentence(@RequestParam String sentence, Model model) {
        try {
            String translatedSentence = translateService.getTranslatedSentence(sentence);
            model.addAttribute("translatedSentence", translatedSentence);
        } catch (RuntimeException e) {
            model.addAttribute("translatedSentence", "번역 중 오류가 발생했습니다.");
        }
        return "main";
    }
}

*/