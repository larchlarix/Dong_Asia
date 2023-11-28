package com.example.DongAisa.controller;

import com.example.DongAisa.dto.NewsDto;
import com.example.DongAisa.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    NewsService newsService;

    /*
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String home(){
        return "main";
    }*/

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getNewsList(Model model,
                              @RequestParam(value = "keyword", required = false, defaultValue = "") final String keyword,
                              @RequestParam(value = "sort", required = false, defaultValue = "byDate") final String sort) {
        List<NewsDto> newsDtos = newsService.getNewsList(keyword, sort);
        // 로그에 출력
        System.out.println("NewsDtos: " + newsDtos);
        model.addAttribute("newsList", newsDtos);
        return "main";
    }
}
