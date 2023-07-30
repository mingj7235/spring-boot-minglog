package com.minglog.api.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class MingLogException extends RuntimeException {

    private final Map<String, String> validation = new HashMap<>();

    public MingLogException(String message) {
        super(message);
    }

    public MingLogException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }
}
