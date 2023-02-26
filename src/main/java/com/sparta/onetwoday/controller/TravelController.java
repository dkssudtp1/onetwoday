package com.sparta.onetwoday.controller;

import com.sparta.onetwoday.dto.TravelRequestDto;
import com.sparta.onetwoday.entity.Message;
import com.sparta.onetwoday.security.UserDetailsImpl;
import com.sparta.onetwoday.service.TravelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
public class TravelController {
    private final TravelService travelService;

    //여행정보 작성하기
    @PostMapping("/api/travel")
    public ResponseEntity<Message> createTravel(@RequestBody TravelRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Message message = new Message(true, "게시물 생성 완료", travelService.createTravel(requestDto, userDetails.getUser()));
        return ResponseEntity.ok(message);
    }

    //내가 쓴 글만 리스트로 조회하기
    @PostMapping("/api/travel/mylist")
    public ResponseEntity<Message> getMyList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Message message = new Message(true, "나의 게시물 리스트 성공", travelService.getMyList(userDetails.getUser()));
        return ResponseEntity.ok(message);
    }

    //무작위(랜덤) 리스트 8개만 보여주기
    @GetMapping("/api/travel")
    public ResponseEntity<Message> getRandomList() {
        Message message = new Message(true, "게시물 리스트 성공", travelService.getRandomList());
        return ResponseEntity.ok(message);
    }

    //상세페이지 조회하기
    @GetMapping("/api/travel/{travelId}")
    public ResponseEntity<Message> getDetail(@PathVariable Long travelId) {
        Message message = new Message(true, "상세 페이지 성공", travelService.getDetail(travelId));
        return ResponseEntity.ok(message);
    }

    //여행정보 수정하기
    @PutMapping("/api/travel/{travelId}")
    public ResponseEntity<Message> updateTravel(@PathVariable Long travelId, @RequestBody TravelRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Message message = new Message(true, "수정 성공", travelService.updateTravel(travelId, requestDto, userDetails.getUser()));
        return ResponseEntity.ok(message);
    }
    
    //여행정보 삭제하기
    @DeleteMapping("/api/travel/{travelId}")
    public ResponseEntity<Message> deleteTravel(@PathVariable Long travelId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        travelService.deleteTravel(travelId, userDetails.getUser());
        Message message = new Message(true, "삭제 성공", null);
        return ResponseEntity.ok(message);
    }

    //좋아요하기
    @PostMapping("/api/travel/{travelId}/like")
    public ResponseEntity<Message> likeTravel(@PathVariable Long travelId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Message message = new Message(true, travelService.likeTravel(travelId, userDetails.getUser()), null);
        return ResponseEntity.ok(message);
    }



}
