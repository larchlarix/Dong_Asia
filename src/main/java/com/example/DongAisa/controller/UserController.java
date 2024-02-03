package com.example.DongAisa.controller;

import com.example.DongAisa.domain.User;
import com.example.DongAisa.dto.BookMarkDto;
import com.example.DongAisa.service.BookMarkService;
import com.example.DongAisa.service.CustomUserDetails;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private BookMarkService bookMarkService;

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



//마이페이지 북마크 기능 + (기존)회원 정보 나타내기

    @GetMapping(value = "/mypage")
    public String BookmarksAndUser(Model model,  Authentication authentication) {

        //북마크 기능
        Long userId = getCurrentUserId();
        List<BookMarkDto> bookmarks = bookMarkService.getBookmarksByUserId(userId);
        model.addAttribute("bookmarks", bookmarks);

        //기존 회원 정보 나타내기
        String userEmail = authentication.getName();
        User user = userService.getUserByEmail(userEmail);
        model.addAttribute("userDto", user);

        return "mypage";
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            return ((CustomUserDetails) authentication.getPrincipal()).getUserId();
        }
        return null;
    }

    // 마이페이지 회원정보 수정(userName, userPassword)
    @PostMapping(value = "/edit")
    public ResponseEntity<String> editUser(@RequestBody UserDto userDto, Authentication authentication) {
        String userEmail = authentication.getName();
        User existingUser = userService.getUserByEmail(userEmail);

        // 변경된 사용자 이름 업데이트
        if (!StringUtils.isEmpty(userDto.getUserName())) {
            existingUser.setUserName(userDto.getUserName());
        }

        // 변경된 비밀번호 업데이트 (비밀번호가 비어있지 않은 경우에만 업데이트)
        if (!StringUtils.isEmpty(userDto.getUserPassword())) {
            String encodedPassword = passwordEncoder.encode(userDto.getUserPassword());
            existingUser.setUserPassword(encodedPassword);
        }

        userService.saveUser(existingUser);
        return ResponseEntity.ok("Success");
    }

    @GetMapping(value = "/edit")
    public String UserAndBookmarks(Model model) {

        //북마크 기능
        Long userId = getCurrentUserId();
        List<BookMarkDto> bookmarks = bookMarkService.getBookmarksByUserId(userId);
        model.addAttribute("bookmarks", bookmarks);


        return "editUser";
    }

}


