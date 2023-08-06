package com.minglog.api.exception;

public class Unauthorized extends MingLogException{

    private static final String Message = "인증이 필요합니다.";
    public Unauthorized() {
        super(Message);
    }

    public Unauthorized(Throwable cause) {
        super(Message, cause);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }
}
