package com.backbase.progfun;

public class Message {

    private int code;
    private String message;

    public Message(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
