package com.sparta.onetwoday.controller;

import com.sparta.onetwoday.dto.CustomException;
import com.sparta.onetwoday.dto.Message;
import com.sparta.onetwoday.entity.ExceptionMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.nio.charset.Charset;

import static com.sparta.onetwoday.entity.ExceptionMessage.DUPLICATE_RESOURCE;

@Slf4j
@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = { ConstraintViolationException.class, DataIntegrityViolationException.class})
    protected ResponseEntity<Message> handleDataException() {
        log.error("handleDataException throw Exception : {}", DUPLICATE_RESOURCE);
        return Message.toResponseEntity(DUPLICATE_RESOURCE);
    }

    @ExceptionHandler(value = { CustomException.class })
    protected ResponseEntity<Message> handleCustomException(CustomException e) {
        log.error("handleCustomException throw CustomException : {}", e.getExceptionMessage());
        return Message.toResponseEntity(e.getExceptionMessage());
    }

}
