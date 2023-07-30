package com.minglog.api.exception;

public class InvalidRequest extends MingLogException{

    private static final String MESSAGE = "잘못된 요청 입니다.";
    public InvalidRequest() {
        super(MESSAGE);
    }

    public InvalidRequest (String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    public InvalidRequest(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
