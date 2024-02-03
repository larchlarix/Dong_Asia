package com.example.DongAisa.repository;

import com.example.DongAisa.domain.BookMark;
import com.example.DongAisa.domain.News;
import com.example.DongAisa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookMarkRepository extends JpaRepository<BookMark, Long> {
    Optional<BookMark> findByUserAndNews(User user, News news);
    List<BookMark> findByUser_UserId(Long userId);
}
