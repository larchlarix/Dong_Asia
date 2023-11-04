package com.example.DongAisa.repository;

import com.example.DongAisa.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
}
