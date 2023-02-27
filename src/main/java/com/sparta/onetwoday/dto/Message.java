package com.sparta.onetwoday.dto;

import com.sparta.onetwoday.entity.ExceptionMessage;
import com.sparta.onetwoday.entity.SuccessMessage;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@NoArgsConstructor
public class Message<T> {

    private boolean status;
    private String message;
    private T data;

    public Message(boolean status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static ResponseEntity<Message> toExceptionResponseEntity(ExceptionMessage exceptionMessage) {
        return ResponseEntity
                .status(exceptionMessage.getHttpStatus())
                .body(Message.builder()
                        .status(!exceptionMessage.getHttpStatus().isError())
                        .message(exceptionMessage.getDetail())
                        .data(exceptionMessage)
                        .build()
                );
    }

    public static ResponseEntity<Message> toResponseEntity(SuccessMessage successMessage) {
        return ResponseEntity
                .status(successMessage.getHttpStatus())
                .body(Message.builder()
                        .status(!successMessage.getHttpStatus().isError())
                        .message(successMessage.getDetail())
                        .data(successMessage)
                        .build()
                );
    }
    
    //리턴 값 있을때 사용
    public static ResponseEntity<Message> toResponseEntity(SuccessMessage successMessage, Object data) {
        return ResponseEntity
                .status(successMessage.getHttpStatus())
                .body(Message.builder()
                        .status(!successMessage.getHttpStatus().isError())
                        .message(successMessage.getDetail())
                        .data(data)
                        .build()
                );
    }
}