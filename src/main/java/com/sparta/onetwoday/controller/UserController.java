package com.sparta.onetwoday.controller;

import com.sparta.onetwoday.dto.CustomException;
import com.sparta.onetwoday.dto.LoginRequestDto;
import com.sparta.onetwoday.dto.SignupRequestDto;
import com.sparta.onetwoday.dto.Message;
import com.sparta.onetwoday.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static com.sparta.onetwoday.entity.ExceptionMessage.USER_FORBIDDEN;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @ResponseBody
    @PostMapping("/signup")
    public ResponseEntity<Message> signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto);
    }

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<Message> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }

    @RequestMapping("/forbidden")
    public ResponseEntity<Message> getForbidden() {
        throw new CustomException(USER_FORBIDDEN);
    }
}
