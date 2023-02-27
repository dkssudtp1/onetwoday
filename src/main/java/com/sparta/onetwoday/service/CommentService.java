package com.sparta.onetwoday.service;


import com.sparta.onetwoday.dto.CommentRequestDto;
import com.sparta.onetwoday.dto.CommentResponseDto;
import com.sparta.onetwoday.dto.TravelRequestDto;
import com.sparta.onetwoday.dto.TravelResponseDto;
import com.sparta.onetwoday.entity.Comment;
import com.sparta.onetwoday.entity.Travel;
import com.sparta.onetwoday.entity.User;
import com.sparta.onetwoday.entity.UserRoleEnum;
import com.sparta.onetwoday.jwt.JwtUtil;
import com.sparta.onetwoday.repository.CommentRepository;
import com.sparta.onetwoday.repository.TravelRepository;
import com.sparta.onetwoday.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TravelRepository travelRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;


    //댓글 등록
    @Transactional
    public CommentResponseDto createComment(Long travelId, CommentRequestDto commentRequestDto, User user) {

            Travel travel = travelRepository.findById(travelId).orElseThrow(
                    () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
            );

            Comment comment = commentRepository.save(new Comment(commentRequestDto, travel, user));

            return new CommentResponseDto(comment);
        }
    //댓글 삭제
    public String deleteComment(Long commentId, User user) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        if (hasAuthority(user, comment)) {
            commentRepository.deleteById(commentId);
        } else {
            throw new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다.");
        }
        return "댓글이 삭제 성공.";
    }

    //댓글 리스트
    @Transactional(readOnly = true)
    public List<CommentResponseDto> commentsList(Long travelId) {
        return commentRepository.findByIdOrderByCreatedAtDesc(travelId);
    }
    //권한 확인하기
    public boolean hasAuthority(User user, Comment comment) {
        return user.getId().equals(comment.getUser().getId()) || user.getRole().equals(UserRoleEnum.ADMIN);
    }
}