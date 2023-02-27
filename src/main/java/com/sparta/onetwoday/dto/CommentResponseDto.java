package com.sparta.onetwoday.dto;


import com.sparta.onetwoday.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private String comment;
    private String username;
    private LocalDateTime createdAt;


    public CommentResponseDto(Comment comment) {
        this.comment = comment.getComment();
        this.username = comment.getUser().getUsername();
        this.createdAt = comment.getCreatedAt();
    }
}
