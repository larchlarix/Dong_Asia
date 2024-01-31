package com.example.DongAisa.dto;

public class TranslationDto {
    String translatedTitle;
    String translatedContent;




    public TranslationDto(String translatedTitle, String translatedContent) {
        this.translatedTitle = translatedTitle;
        this.translatedContent = translatedContent;

    }
    public String getTranslatedTitle() {
        return translatedTitle;
    }

    public void setTranslatedTitle(String translatedTitle) {
        this.translatedTitle = translatedTitle;
    }

    public String getTranslatedContent() {
        return translatedContent;
    }

    public void setTranslatedContent(String translatedContent) {
        this.translatedContent = translatedContent;
    }

    public TranslationDto (){}

}
