package com.example.DongAisa.service;

import com.example.DongAisa.domain.BookMark;
import com.example.DongAisa.domain.News;
import com.example.DongAisa.domain.User;
import com.example.DongAisa.dto.BookMarkDto;
import com.example.DongAisa.repository.BookMarkRepository;
import com.example.DongAisa.repository.NewsRepository;
import com.example.DongAisa.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class BookMarkService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookMarkRepository bookMarkRepository;
    private static final Logger log = LoggerFactory.getLogger(BookMarkService.class);


    @Transactional
    public void insert(BookMarkDto bookMarkDto) {
        try {
            User user = userRepository.findById(bookMarkDto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find user id: " + bookMarkDto.getUserId())));

            News news = newsRepository.findById(bookMarkDto.getNewsId())
                    .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find user id: " + bookMarkDto.getUserId())));

            if (bookMarkRepository.findByUserAndNews(user, news).isPresent()) {
                throw new EntityNotFoundException(String.format("already exist data by user id :" + user.getUserId() + " ,"
                        + "news id : " + news.getNewsId()));
            }

            BookMark bookMark = BookMark.builder()
                    .news(news)
                    .user(user)
                    .build();

            bookMarkRepository.save(bookMark);

            newsRepository.addLikeCount(news);
        } catch (RuntimeException e) {
            log.error("Error occurred during bookmark insertion", e);
            throw e; // 롤백을 위해 예외를 다시 던진다.
        }
    }

    @Transactional
    public void delete(BookMarkDto bookMarkDto) {
        try {
            User user = userRepository.findById(bookMarkDto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find user id: " + bookMarkDto.getUserId())));

            News news = newsRepository.findById(bookMarkDto.getNewsId())
                    .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find user id: " + bookMarkDto.getUserId())));

            BookMark bookMark = bookMarkRepository.findByUserAndNews(user, news)
                    .orElseThrow(() -> new EntityNotFoundException(String.format("Could not find user id: ")));

            bookMarkRepository.delete(bookMark);

            newsRepository.subLikeCount(news);
        } catch (RuntimeException e) {
            log.error("Error occurred during bookmark deletion", e);
            throw e; // 롤백을 위해 예외를 다시 던진다.
        }
    }

    @Transactional
    public List<BookMarkDto> getBookmarksByUserId(Long userId) {
        List<BookMark> bookmarks = bookMarkRepository.findByUser_UserId(userId);

        return bookmarks.stream()
                .map(bookmark -> {
                    Long newsId = bookmark.getNews().getNewsId();
                    News news = newsRepository.findById(newsId)
                            .orElseThrow(() -> new EntityNotFoundException("Could not find news id: " + newsId));

                    return new BookMarkDto(userId, newsId, news.getNewsTitle(), news.getNewsDate(), news.getNewsImageURL(),news.getNewsPublisher());
                })
                .collect(Collectors.toList());
    }
}



