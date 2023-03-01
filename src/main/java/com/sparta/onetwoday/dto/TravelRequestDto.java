package com.sparta.onetwoday.dto;

import com.sparta.onetwoday.entity.Travel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class TravelRequestDto {
    private String title;
    private MultipartFile images = null;
    private String content;
    private Integer budget;

}
