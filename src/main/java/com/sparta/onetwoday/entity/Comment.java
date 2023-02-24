package com.sparta.onetwoday.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comments;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID", nullable = false)
    private Board board;

    public Comment(CommentRequestDto requestDto, Board board, User user) {
        this.user = user;
        this.board = board;
        this.comments = requestDto.getComments();
    }

    public void updateComment(CommentRequestDto commentRequestDto) {
        this.comments = commentRequestDto.getComments();
    }
}