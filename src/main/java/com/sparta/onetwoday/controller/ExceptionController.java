package com.sparta.onetwoday.controller;

import com.sparta.onetwoday.entity.Message;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.charset.Charset;

@ControllerAdvice
public class ExceptionController {

    // 400
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> BadRequestException(final RuntimeException ex) {
        Message message = new Message(false, ex.getMessage(), ex);

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
    }

    // 401
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity handleAccessDeniedException(final AccessDeniedException ex) {
        Message message = new Message(false, ex.getMessage(), ex);

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(message, headers, HttpStatus.UNAUTHORIZED);
    }

    //정규식
    @ExceptionHandler({BindException.class})
    public ResponseEntity bindException(BindException ex) {
        Message message = new Message(false, ex.getFieldError().getDefaultMessage(), ex.getBindingResult().getTarget());

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
    }
    // 500
    @ExceptionHandler({Exception.class})
    public ResponseEntity handleAll(final Exception ex) {
        Message message = new Message(false, ex.getMessage(), ex);

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(message, headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
