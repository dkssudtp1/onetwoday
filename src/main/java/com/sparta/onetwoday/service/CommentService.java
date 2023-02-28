package com.sparta.onetwoday.service;


import com.sparta.onetwoday.dto.*;
import com.sparta.onetwoday.entity.Comment;
import com.sparta.onetwoday.entity.Travel;
import com.sparta.onetwoday.entity.User;
import com.sparta.onetwoday.entity.UserRoleEnum;
import com.sparta.onetwoday.repository.CommentRepository;
import com.sparta.onetwoday.repository.TravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.sparta.onetwoday.entity.ExceptionMessage.*;
import static com.sparta.onetwoday.entity.SuccessMessage.*;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TravelRepository travelRepository;


    //댓글 등록
    @Transactional
    public ResponseEntity<Message> createComment(Long travelId, CommentRequestDto commentRequestDto, User user) {

        if (travelRepository.findByIdAndIsDeleted(travelId,false) == null) {
            throw new CustomException(BOARD_NOT_FOUND);
        }
        Travel travel = travelRepository.findByIdAndIsDeleted(travelId, false);
        Comment comment = commentRepository.save(new Comment(commentRequestDto, travel, user));

        return new Message().toResponseEntity(COMMENT_POST_SUCCESS, new CommentResponseDto(comment));
    }

    //댓글 삭제
    public ResponseEntity<Message> deleteComment(Long travelId, Long commentId, User user) {
        Comment comment = commentRepository.findByIdAndIsDeleted(commentId, false);
        if(comment == null) {
            throw new CustomException(COMMENT_NOT_FOUND);
        }
        if (hasAuthority(user, comment)) {
            comment.setIsDeleted();
            commentRepository.saveAndFlush(comment);
        } else {
            throw new CustomException(UNAUTHORIZED_UPDATE_OR_DELETE);
        }

        return new Message().toResponseEntity(COMMENT_DELETE_SUCCESS, getCommentList(travelId));
    }

    //댓글 리스트
    @Transactional(readOnly = true)
    public List<CommentResponseDto> getCommentList(Long travelId) {
        List<Comment> comments = commentRepository.findByTravelIdAndIsDeletedOrderByCreatedAtDesc(travelId, false);
        List<CommentResponseDto> responseDtos = new ArrayList<>();

        if (!comments.isEmpty()) {
            for (Comment comment : comments) {
                responseDtos.add(new CommentResponseDto(comment));
            }
        }

        return responseDtos;
    }

    //권한 확인하기
    public boolean hasAuthority(User user, Comment comment) {
        return user.getId().equals(comment.getUser().getId()) || user.getRole().equals(UserRoleEnum.ADMIN);
    }
}