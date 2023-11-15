package com.example.DongAisa.domain;

import jakarta.persistence.*;
@Entity
@Table(name="newsTest", schema="dong_asia", uniqueConstraints = {@UniqueConstraint(columnNames = "newsTest_id")})
public class NewsTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "newsTest_id", unique = true, nullable = false)
    private Long newsTestId;

    @Column(name="newsTest_title",unique = false, nullable = false)
    private String newsTestTitle;

    @Column(columnDefinition = "LONGTEXT",name="newsTest_body",unique = false, nullable = false)
    private String newsTestBody;
    @Column(name="newsTest_date",unique = false, nullable = false)
    private String newsTestDate;

    public NewsTest(){};

    public Long getNewsTestId() {
        return newsTestId;
    }

    public void setNewsTestId(Long newsTestId) {
        this.newsTestId = newsTestId;
    }

    public String getNewsTestTitle() {
        return newsTestTitle;
    }

    public void setNewsTestTitle(String newsTestTitle) {
        this.newsTestTitle = newsTestTitle;
    }

    public String getNewsTestBody() {
        return newsTestBody;
    }

    public void setNewsTestBody(String newsTestBody) {
        this.newsTestBody = newsTestBody;
    }

    public String getNewsTestDate() {
        return newsTestDate;
    }

    public void setNewsTestDate(String newsTestDate) {
        this.newsTestDate = newsTestDate;
    }

}
