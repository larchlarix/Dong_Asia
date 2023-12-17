package com.example.DongAisa.service;

import com.example.DongAisa.domain.User;

import com.example.DongAisa.dto.UserDto;

import com.example.DongAisa.mapper.UserMapper;

import com.example.DongAisa.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final HttpSession httpSession;

    @Autowired
    public UserService(HttpSession httpSession) {
        this.httpSession = httpSession;
    }





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

    // 사용자 로그인
    public boolean login(UserDto userDto) {
        Optional<User> userOptional = userRepository.findByUserEmail(userDto.getUserEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // 비밀번호 일치 여부 확인
            return passwordEncoder.matches(userDto.getUserPassword(), user.getUserPassword());
        }
        return false;
    }

    // 사용자 로그아웃
    public void logout() {
        httpSession.invalidate();

    }


/*
// 사용자 로그인
public boolean login(UserDto userDto) {
    Optional<User> userOptional = userRepository.findByUserEmail(userDto.getUserEmail());
    if (userOptional.isPresent()) {
        User user = userOptional.get();
        // 비밀번호 일치 여부 확인
        if (passwordEncoder.matches(userDto.getUserPassword(), user.getUserPassword())) {
            // 로그인 성공 시 세션에 사용자 정보 저장
            httpSession.setAttribute("user", user);
            return true;
        }
    }
    return false;
}


    // 사용자 로그아웃
    public void logout() {
        // 세션 무효화
        httpSession.invalidate();
    }

    // 현재 로그인된 사용자 정보 가져오기
    public User getCurrentUser() {
        return (User) httpSession.getAttribute("user");
    }

    public boolean isLoggedIn() {
        boolean result = getCurrentUser() != null;
        System.out.println("Is logged in: " + result);
        return result;
    }
    */


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
}
/*
    // 유저 회원가입
    public UserDto createUser(UserDto userDto) {
        if(userRepository.findByUserEmail(userDto.getUserEmail()).isPresent()){
            throw new ExecutionControl.UserException(UserExceptionEnum.DUPLICATED_SIGNUP_EMAIL.getErrormessage());
        }
        User user = UserMapper.convertToModel(userDto);
        this.userRepository.save(user);
        return UserMapper.convertToDto(user);
    }



    //유저 name 변경
    public UserDto changeUserName(Long UserId, UserDto userDto){
        Optional<User> user = this.userRepository.findById(UserId);
        if(user.isEmpty()){
            throw new NoSuchElementException(String.format("User ID '%d' 가 존재하지 않습니다", UserId));
        }
        User updateUser = user.get();
        updateUser.setUserName(userDto.getUserName());
        User savedUser = this.userRepository.save(updateUser);
        return UserMapper.convertToDto(savedUser);
    }

    //유저 password 변경
    public UserDto changeUserPassword(Long UserId, UserDto userDto){
        Optional<User> user = this.userRepository.findById(UserId);
        if(user.isEmpty()){
            throw new NoSuchElementException(String.format("User ID '%d' 가 존재하지 않습니다", UserId));
        }
        User updateUser = user.get();
        updateUser.setUserPassword(userDto.getUserPassword());
        User savedUser = this.userRepository.save(updateUser);
        return UserMapper.convertToDto(savedUser);
    }

    //유저 탈퇴

    public UserDto deleteUser(Long userId){
        Optional<User> user = this.userRepository.findById(userId);
        if(user.isEmpty()){
            throw new NoSuchElementException(String.format("User ID '%d' 가 존재하지 않습니다", userId));
        }
        this.userRepository.deleteById(userId);
        return UserMapper.convertToDto(user.get());
    }
}
*/