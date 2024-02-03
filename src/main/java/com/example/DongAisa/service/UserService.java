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
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


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
        if(existingUser.isPresent()){ // Optional이 비어있지 않으면 중복된 사용자가 존재
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    //로그인


    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        Optional<User> existingUser = userRepository.findByUserEmail(userEmail);
        if (existingUser.isEmpty()) {
            throw new UsernameNotFoundException(userEmail);
        }
        User user = existingUser.get();



        // 역할을 SimpleGrantedAuthority로 생성
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().toString()));

        return new CustomUserDetails(
                user.getUserId(), // 추가된 부분
                user.getUserEmail(),
                user.getUserPassword(),
                authorities
        );
    }


}


    /*
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException{
        Optional<User> existingUser = userRepository.findByUserEmail(userEmail);
        if(existingUser.isEmpty()){
            throw new UsernameNotFoundException(userEmail);
        }
        User user = existingUser.get();
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserEmail())
                .password(user.getUserPassword())
                .roles(user.getRole().toString())
                .build();
    }


     */


