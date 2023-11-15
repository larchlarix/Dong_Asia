package com.example.DongAisa.repository;

import com.example.DongAisa.domain.News;
import com.example.DongAisa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserEmail(String userEmail);


}
