package com.example.DongAisa.repository;

import com.example.DongAisa.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findByNewsTitleContainingOrderByNewsDateAsc(String Keyword);
    List<News> findByNewsTitleContaining(String Keyword);

}
