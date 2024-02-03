package com.example.DongAisa.repository;

import com.example.DongAisa.domain.News;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.DongAisa.domain.QNews.news;

@Repository
public abstract class NewsRepositoryImpl implements NewsRepository{
    private final JPAQueryFactory queryFactory;

    @Autowired
    public NewsRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }


    public void addLikeCount(News selectedNews) {
        queryFactory.update(news)
                .set(news.likeCount, news.likeCount.add(1))
                .where(news.eq(selectedNews))
                .execute();
    }


    public void subLikeCount(News selectedNews) {
        queryFactory.update(news)
                .set(news.likeCount, news.likeCount.subtract(1))
                .where(news.eq(selectedNews))
                .execute();
    }

}
