package com.example.DongAisa.repository;

import com.example.DongAisa.domain.News;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    List<News> findByNewsTitleContainingOrderByNewsDateDesc(String Keyword);

    Page<News> findByNewsTitleContainingOrderByNewsDateDesc(String keyword, Pageable pageable);
    Page <News> findByNewsTitleContaining(String Keyword, Pageable pageable);

    List <News> findByNewsTitleContaining(String Keyword);

    List<News> findByNewsCategoryInAndNewsPublisherIn(List<Long> categories, List<Long> publishers);

    Page<News> findByNewsCategoryInAndNewsPublisherIn(List<Long> categories, List<Long> publishers,Pageable pageable);



}
