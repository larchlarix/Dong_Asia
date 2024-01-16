package com.example.DongAisa.controller;

import com.example.DongAisa.dto.NewsDto;
import com.example.DongAisa.service.NewsService;
import com.example.DongAisa.service.TranslateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class TranslateController {

    @Autowired
    TranslateService translateService;

    @Autowired
    NewsService newsService;;


}
