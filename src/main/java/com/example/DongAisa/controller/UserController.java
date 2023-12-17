package com.example.DongAisa.controller;

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
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    //유저 조회
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<UserDto> getUser(@PathVariable("userId") final long userId) {
        UserDto user = userService.getUser(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);

    }

    /*
    // 로그인 엔드포인트
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto) {
        // 로그인 로직을 UserService에서 처리하도록 가정
        if (userService.login(userDto)) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        }
    }

    // 로그아웃 엔드포인트
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // 로그아웃 로직을 UserService에서 처리하도록 가정
        userService.logout();
        return ResponseEntity.ok("Logout successful");
    }

     */
    /*
// 로그인 엔드포인트
    @GetMapping("/login")
    public String  login(Model model){
        //model.addAttribute("userDto", new UserDto());
        return "login";
    }
    @PostMapping("/login")
    public String login( UserDto userDto, Model model) {
        if (userService.login(userDto)) {
            // 로그인 성공 시 메인 페이지로 리다이렉션
            return "redirect:/";
        } else {
            model.addAttribute("message", "Login failed");
            return "login";
        }
    }

    // 로그아웃 엔드포인트
    @PostMapping("/logout")
    public String logout(Model model) {
        userService.logout();
        model.addAttribute("message", "Logout successful");
        return "redirect:/";
    }

     */

    // 로그인 페이지 보여주기
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        // 로그인 폼에 필요한 모델 속성 추가
        //model.addAttribute("userDto", new UserDto());
        //model.addAttribute("userService", userService); // 로그인 상태 체크를 위한 userService 추가
        //model.addAttribute("isLoggedIn", userService.isLoggedIn());
        return "login";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@ModelAttribute("userDto") UserDto userDto, Model model) {
        if (userService.login(userDto)) {
            // 로그인 성공 시 메인 페이지로 리다이렉션
            return "redirect:/";
        } else {
            model.addAttribute("message", "로그인 실패");
            return "login";
        }
    }

    // 로그아웃 처리
    @PostMapping("/logout")
    public String logout(RedirectAttributes redirectAttributes) {
        userService.logout();
        // Flash Attribute를 사용하여 리다이렉트 시 메시지 전달
        redirectAttributes.addFlashAttribute("message", "로그아웃 성공");
        return "redirect:/";
    }

    // 회원가입 엔드포인트
    /*
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody UserDto userDto) {
        // 회원가입 로직을 UserService에서 처리하도록 가정
        if (userService.signUp(userDto)) {
            return ResponseEntity.ok("Signup successful");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists");
        }
    }
}

     */

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

}

    /*
    //유저 회원가입
    @RequestMapping(value="/signup",method = RequestMethod.POST)
    public ResponseEntity<UserDto> createUser(@RequestBody final UserDto userDto){
        UserDto savedUserDto = userService.createUser(userDto);
        return new ResponseEntity<>(savedUserDto, HttpStatus.OK);
    }


    // 유저 name 변경
    @RequestMapping(value="/{userId}/name", method = RequestMethod.PUT)
    public ResponseEntity <UserDto> updateUser(@PathVariable("userId") final long userId,
                                               @RequestBody final UserDto userDto){
        UserDto res = userService.changeUserName(userId,userDto);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    //유저 password 변경
    @RequestMapping(value="/{userId}/password", method = RequestMethod.PUT)
    public ResponseEntity <UserDto> updateUser_(@PathVariable("userId") final long userId,
                                               @RequestBody final UserDto userDto){
        UserDto res = userService.changeUserPassword(userId,userDto);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    //유저 탈퇴
    @RequestMapping(value="/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity <UserDto> deleteUser(@PathVariable("userId") final long userId){
        UserDto deleteUserDto = userService.deleteUser(userId);
        return new ResponseEntity<>(deleteUserDto, HttpStatus.OK);
    }

    //유저 로그인

    //유저 로그아웃
}
*/