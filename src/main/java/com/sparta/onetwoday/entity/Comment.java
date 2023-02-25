package com.sparta.onetwoday.entity;

import com.sparta.onetwoday.dto.CommentRequestDto;
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
    @JoinColumn(name = "TRAVEL_ID", nullable = false)
    private Travel travel;

    public Comment(CommentRequestDto requestDto, Travel travel, User user) {
        this.user = user;
        this.travel = travel;
        this.comments = requestDto.getComments();
    }

    public void updateComment(CommentRequestDto commentRequestDto) {
        this.comments = commentRequestDto.getComments();
    }
}