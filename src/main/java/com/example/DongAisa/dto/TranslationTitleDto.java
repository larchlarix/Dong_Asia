package com.example.DongAisa.dto;

public class TranslationTitleDto {

    String[] titles;

    // 생성자, 게터 및 세터


    public TranslationTitleDto(String[] titles) {
        this.titles = titles;


    }


    public String[] getTitles() {
        return titles;
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    public TranslationTitleDto (){}
}
