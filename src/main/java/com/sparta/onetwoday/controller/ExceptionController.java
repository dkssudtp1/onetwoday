package com.sparta.onetwoday.controller;

import com.sparta.onetwoday.dto.CustomException;
import com.sparta.onetwoday.dto.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

import static com.sparta.onetwoday.entity.ExceptionMessage.DUPLICATE_RESOURCE;

@Slf4j
@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = { ConstraintViolationException.class, DataIntegrityViolationException.class})
    protected ResponseEntity<Message> handleDataException() {
        log.error("handleDataException throw Exception : {}", DUPLICATE_RESOURCE);
        return Message.toExceptionResponseEntity(DUPLICATE_RESOURCE);
    }

    @ExceptionHandler(value = { CustomException.class })
    protected ResponseEntity<Message> handleCustomException(CustomException e) {
        log.error("handleCustomException throw CustomException : {}", e.getExceptionMessage());
        return Message.toExceptionResponseEntity(e.getExceptionMessage());
    }
}
