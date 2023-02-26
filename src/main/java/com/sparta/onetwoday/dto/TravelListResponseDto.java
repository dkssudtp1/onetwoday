package com.sparta.onetwoday.dto;

import com.sparta.onetwoday.entity.Travel;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class TravelListResponseDto {
    private Long id;
    private String title;
    private String images;

    private Long likeCount;

    public TravelListResponseDto(Travel travel, Long likeCount) {
        this.id = travel.getId();
        this.title = travel.getTitle();
        this.images = travel.getImages();
        this.likeCount = likeCount;
    }

    public TravelListResponseDto(Long id, String title, String images, Long likeCount) {
        this.id = id;
        this.title = title;
        this.images = images;
        this.likeCount = likeCount;
    }
}
