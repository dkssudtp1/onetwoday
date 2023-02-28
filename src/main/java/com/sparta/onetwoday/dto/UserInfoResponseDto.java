package com.sparta.onetwoday.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoResponseDto {
    private Long id;
    private String nickname;

    public UserInfoResponseDto(Long id, String nickname) {
        this. id = id;
        this.nickname = nickname;
    }
}
