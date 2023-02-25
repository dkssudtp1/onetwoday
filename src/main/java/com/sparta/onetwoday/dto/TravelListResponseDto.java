package com.sparta.onetwoday.dto;

import com.sparta.onetwoday.entity.Travel;
import lombok.Getter;

@Getter
public class TravelListResponseDto {
    private Long id;
    private String title;
    private String images;

    private Integer likeCount;

    public TravelListResponseDto(Travel travel) {
        this.id = travel.getId();
        this.title = travel.getTitle();
        this.images = travel.getImages();
        this.likeCount = 0;
    }
}
