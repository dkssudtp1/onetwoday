package com.sparta.onetwoday.controller;

import com.sparta.onetwoday.dto.TravelListRequestDto;
import com.sparta.onetwoday.dto.TravelRequestDto;
import com.sparta.onetwoday.dto.Message;
import com.sparta.onetwoday.entity.User;
import com.sparta.onetwoday.jwt.JwtUtil;
import com.sparta.onetwoday.security.UserDetailsImpl;
import com.sparta.onetwoday.service.TravelService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
public class TravelController {
    private final TravelService travelService;

    //여행정보 작성하기
    @PostMapping(consumes = {"multipart/form-data"},
            value = "/api/travel")
    public ResponseEntity<Message> createTravel(@ModelAttribute TravelRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return travelService.createTravel(requestDto, userDetails.getUser());
    }

    //내가 쓴 글만 리스트로 조회하기
    @GetMapping("/api/travel/mylist")
    public ResponseEntity<Message> getMyList(@RequestHeader(value="Authorization") String jwt) {
        return travelService.getMyList(jwt);
    }

    //무작위(랜덤) 리스트 8개만 보여주기
    @GetMapping("/api/travel")
    public ResponseEntity<Message> getRandomList() {
        return travelService.getRandomList();
    }

    @PostMapping ("/api/travel")
    public ResponseEntity<Message> getbudgetFilterRandomList(@RequestBody TravelListRequestDto travelListRequestDto) {
        if(travelListRequestDto.getBudgetFilter() == 0)
            return travelService.getRandomList();
        else
            return travelService.getbudgetFilterRandomList(travelListRequestDto);
    }

    //상세페이지 조회하기
    @GetMapping("/api/travel/{travelId}")
    public ResponseEntity<Message> getDetail(@PathVariable Long travelId) {
        return travelService.getDetail(travelId);
    }

    //여행정보 수정하기
    @PutMapping(consumes = {"multipart/form-data"},
            value = "/api/travel/{travelId}")
    public ResponseEntity<Message> updateTravel(@PathVariable Long travelId, @ModelAttribute TravelRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return travelService.updateTravel(travelId, requestDto, userDetails.getUser());
    }

    //여행정보 삭제하기
    @DeleteMapping("/api/travel/{travelId}")
    public ResponseEntity<Message> deleteTravel(@PathVariable Long travelId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return travelService.deleteTravel(travelId, userDetails.getUser());
    }

    //좋아요하기
    @PostMapping("/api/travel/{travelId}/like")
    public ResponseEntity<Message> likeTravel(@PathVariable Long travelId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return travelService.likeTravel(travelId, userDetails.getUser());
    }


}
