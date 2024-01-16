package com.example.DongAisa.service;

import com.example.DongAisa.domain.User;

import com.example.DongAisa.dto.UserDto;

import com.example.DongAisa.mapper.UserMapper;

import com.example.DongAisa.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {


    @Autowired
    private UserRepository userRepository;


    //유저 조회
    public UserDto getUser(Long userId) {
        Optional<User> res = userRepository.findById(userId);
        if (res.isPresent()) {
            UserDto userDto = UserMapper.convertToDto(res.get());
            return userDto;
        } else {
            throw new EntityNotFoundException(String.format("user 아이디 %d로 조회되지 않았습니다", userId));
        }
    }

    //유저 회원가입
    public User saveUser(User user){
        validateDuplicateUser(user);
        return userRepository.save(user);

    }
    private  void validateDuplicateUser(User user){
        Optional<User> existingUser = userRepository.findByUserEmail(user.getUserEmail());
        if(existingUser != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }


/*
    // 사용자 회원가입
    public boolean signUp(UserDto userDto) {
        // 이메일을 통한 중복 체크
        Optional<User> existingUser = userRepository.findByUserEmail(userDto.getUserEmail());

        if (existingUser.isEmpty()) {
            // 새로운 사용자 생성
            User user = User.builder()
                    .userEmail(userDto.getUserEmail())
                    .userName(userDto.getUserName())
                    .userPassword(passwordEncoder.encode(userDto.getUserPassword()))
                    .build();

            // 데이터베이스에 저장
            userRepository.save(user);
            return true;
        } else {
            // 이미 등록된 이메일이라면 회원가입 실패
            return false;
        }
    }

 */
}
