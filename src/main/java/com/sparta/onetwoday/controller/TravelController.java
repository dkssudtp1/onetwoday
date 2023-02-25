package com.sparta.onetwoday.controller;

import com.sparta.onetwoday.dto.TravelRequestDto;
import com.sparta.onetwoday.entity.Message;
import com.sparta.onetwoday.security.UserDetailsImpl;
import com.sparta.onetwoday.service.TravelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
public class TravelController {
    private final TravelService travelService;

    @PostMapping("/api/travel")
    public ResponseEntity<Message> createTravel(@RequestBody TravelRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Message message = new Message(true, "게시물 생성 완료", travelService.createTravel(requestDto, userDetails.getUser()));
        return ResponseEntity.ok(message);
    }
}
