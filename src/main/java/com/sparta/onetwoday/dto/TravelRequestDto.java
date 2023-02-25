package com.sparta.onetwoday.dto;

import com.sparta.onetwoday.entity.Travel;
import lombok.Getter;

@Getter
public class TravelRequestDto {
    private String title;
    private String images;
    private String content;
    private Integer budget;

}
