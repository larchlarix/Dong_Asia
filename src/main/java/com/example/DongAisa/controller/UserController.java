package com.example.DongAisa.controller;

import com.example.DongAisa.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import com.example.DongAisa.dto.UserDto;
import com.example.DongAisa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    //유저 조회
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<UserDto> getUser(@PathVariable("userId") final long userId) {
        UserDto user = userService.getUser(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);

    }

    @GetMapping(value = "/signup")
    public String userForm(Model model){
        model.addAttribute("userDto", new UserDto());
        return "signup";
    }




/*
// 회원가입 엔드포인트
    @GetMapping("/signup")
    public String signUp(Model model){
        //model.addAttribute("message", "User already exists");
        model.addAttribute("userDto", new UserDto());
        return "signup";
    }
    @PostMapping("/signup")
    public String signUp(UserDto userDto) {
        userService.signUp(userDto);
            // 회원가입 성공 시 메인 페이지
        return "redirect:/";


   }
*/
}


