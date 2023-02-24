package com.sparta.onetwoday.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Message {

    private boolean status;
    private String message;
    private Object data;

    public Message(boolean status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}