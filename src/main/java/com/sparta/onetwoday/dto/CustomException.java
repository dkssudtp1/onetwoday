package com.sparta.onetwoday.dto;

import com.sparta.onetwoday.entity.ExceptionMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private final ExceptionMessage exceptionMessage;
}
