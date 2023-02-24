package com.sparta.onetwoday.controller;

import com.sparta.onetwoday.dto.LoginRequestDto;
import com.sparta.onetwoday.dto.SignupRequestDto;
import com.sparta.onetwoday.entity.Message;
import com.sparta.onetwoday.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @ResponseBody
    @PostMapping("/signup")
    public ResponseEntity<Message> signup(@Valid SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);

        Message message = new Message(true, "회원가입 완료", null);

        return ResponseEntity.ok(message);
    }

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<Message> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        userService.login(loginRequestDto, response);

        Message message = new Message(true, "로그인 완료", null);

        return ResponseEntity.ok(message);
    }

    @RequestMapping("/forbidden")
    public ResponseEntity<Message> getForbidden() {
        Message message = new Message(false, "로그인을 안했거나 권한이 없습니다.", null);

        return ResponseEntity.badRequest().body(message);
    }
}
