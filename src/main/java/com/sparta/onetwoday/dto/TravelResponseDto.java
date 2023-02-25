package com.sparta.onetwoday.dto;

import com.sparta.onetwoday.entity.Travel;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TravelResponseDto {
    private Long id;
    private String title;
    private String username;
    private String content;
    private String images;
    private Integer budget;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;

    public TravelResponseDto(Travel travel) {
        this.id = travel.getId();
        this.username = travel.getUser().getUsername();
        this.title = travel.getTitle();
        this.content = travel.getContent();
        this.images = travel.getImages();
        this.budget = travel.getBudget();
        this.modifiedAt = travel.getModifiedAt();
        this.createdAt = travel.getCreatedAt();
    }
}
