package com.example.DongAisa.dto;

public class KeywordData {
    private String startdate;

    private String enddate;
    private String keyword;


    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }


    public String getStartdate() {return startdate;}

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public KeywordData() {
        // 기본 생성자
    }
}
