package com.sparta.onetwoday.entity;

import com.sparta.onetwoday.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "TRAVEL_ID", nullable = false)
    private Travel travel;

    private Boolean isDeleted;

    public Comment(CommentRequestDto requestDto, Travel travel, User user) {
        this.user = user;
        this.travel = travel;
        this.comment = requestDto.getComment();
        this.isDeleted = false;
    }

    public void setIsDeleted() {
        this.isDeleted = true;
    }
}