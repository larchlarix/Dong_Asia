package com.example.DongAisa.controller;

import com.example.DongAisa.domain.User;
import jakarta.validation.Valid;
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

    @PostMapping(value = "/signup")
    public String newUser(@Valid UserDto userDto){
        User user = User.createUser(userDto, passwordEncoder);
        userService.saveUser(user);
        return "redirect:/";

    }

    @GetMapping(value="/login")
    public String loginUser
        (@RequestParam(value = "error", required = false) String error,
                @RequestParam(value = "exception", required = false) String exception,
                Model model) {
            model.addAttribute("error", error);
            model.addAttribute("exception", exception);
        return "login";
    }

    @PostMapping(value = "/login")
    public String login() {
        // Spring Security에서 자동으로 처리되므로 별도의 로그인 처리는 필요 없습니다.
        return "redirect:/";
    }

    /*
    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg","아이디 또는 비밀번호를 확인해주세요");
        return "login";
    }

*/

    @GetMapping(value = "/mypage")
    public String userMypage(){
        return "mypage";
    }

}


