package com.sparta.onetwoday.controller;

import com.sparta.onetwoday.dto.CommentRequestDto;
import com.sparta.onetwoday.dto.CommentResponseDto;
import com.sparta.onetwoday.service.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

public class CommentController {
    private final CommentService commentService;

    //댓글 작성
    @PostMapping("/comment/{id}")
    public CommentResponseDto createComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        return commentService.createComment(id, commentRequestDto, request);
    }

    //댓글 수정
//    @PutMapping("/comment/{id}")
//    public CommentResponseDto updateComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
//        return commentService.updateComment(id, commentRequestDto, request);
//    }

    //댓글 삭제
    @DeleteMapping("/comment/{id}")
    public String deleteComment(@PathVariable Long id, HttpServletRequest request) {
        return commentService.deleteComment(id, request);
    }
}
