package com.minglog.api.exception;

public class PostNotFound extends RuntimeException{

    private static final String Message = "존재하지 않는 글입니다.";
    public PostNotFound() {
        super(Message);
    }

    public PostNotFound(Throwable cause) {
        super(Message, cause);
    }
}
