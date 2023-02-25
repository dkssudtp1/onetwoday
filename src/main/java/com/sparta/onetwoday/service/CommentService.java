package com.sparta.onetwoday.service;

import com.sparta.onetwoday.dto.CommentRequestDto;
import com.sparta.onetwoday.dto.CommentResponseDto;
import com.sparta.onetwoday.entity.Comment;
import com.sparta.onetwoday.entity.User;
import com.sparta.onetwoday.jwt.JwtUtil;
import com.sparta.onetwoday.repository.CommentRepository;
import com.sparta.onetwoday.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TravelRepository boardRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    //댓글 등록
    @Transactional
    public CommentResponseDto createComment(Long id, CommentRequestDto commentRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Travel travel = boardRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
            );

            Comment comment = commentRepository.save(new Comment(commentRequestDto, travel, user));

            return new CommentResponseDto(comment);
        } else {
            return null;
        }
    }

//    //댓글 수정
//    @Transactional
//    public CommentResponseDto updateComment(Long id, CommentRequestDto commentRequestDto, HttpServletRequest request) {
//        String token = jwtUtil.resolveToken(request);
//        Claims claims;
//
//        if (token != null) {
//            if (jwtUtil.validateToken(token)) {
//                claims = jwtUtil.getUserInfoFromToken(token);
//            } else {
//                throw new IllegalArgumentException("Token Error");
//            }
//
//            Comment comment = commentRepository.findById(id).orElseThrow(
//                    () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
//            );
//
//            if (!claims.getSubject().equals(comment.getUser().getUsername())) {
//                throw new IllegalArgumentException("작성자가 다릅니다.");
//
//            } else {
//                comment.updateComment(commentRequestDto);
//                return new CommentResponseDto(comment);
//            }
//        }
//        return null;
//    }

    //댓글 삭제
    public String deleteComment(Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            Comment comment = commentRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
            );

            if (!claims.getSubject().equals(comment.getUser().getUsername())) {
                throw new IllegalArgumentException("작성자가 다릅니다.");
            } else {
                commentRepository.deleteById(id);
                return "댓글이 삭제됐습니다.";
            }
        }
        return "토큰이 없습니다";
    }

    //댓글 리스트
    @Transactional(readOnly = true)
    public List<CommentResponseDto> commentsList(Long id) {
        return commentRepository.findByIdOrderByCreatedAtDesc(id);
    }
}