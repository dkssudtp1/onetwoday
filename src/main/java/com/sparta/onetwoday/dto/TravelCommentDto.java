package com.sparta.onetwoday.dto;

import com.sparta.onetwoday.entity.Travel;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class TravelCommentDto {
//    private Long id;
    private String title;
    private String username;
    private String images;
    private String content;
    private Integer budget;
    private Long likeCount; //좋아요 구현되면 Long으로 바꿔야함
    private LocalDateTime createdAt;

    private List<CommentResponseDto> comments;
//    private String comments;

    public TravelCommentDto(Travel travel, Long likeCount, List<CommentResponseDto> comments) {
//        this.id = travel.getId();
        this.title = travel.getTitle();
        this.username = travel.getUser().getUsername();
        this.images = travel.getImages();
        this.content = travel.getContent();
        this.budget = travel.getBudget();
        this.likeCount = likeCount;
        this.createdAt = travel.getCreatedAt();
        this.comments = comments;

    }
}
