package com.minglog.api.response;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 * {
 * "code" : "400",
 * "message" : "잘못된 요청입니다.",
 * "validation" : {
 *      "title": "값을 입력해주세요"
 *      ...
 *      }
 * }
 */
@Getter
// @JsonInclude(value = JsonInclude.Include.NON_EMPTY) // 비어 있는 필드는 응답값으로 내려보내지 않도록 함
public class ErrorResponse {

    private final String code;

    private final String message;

    private final Map<String, String> validation;

    @Builder
    public ErrorResponse(final String code, final String message, Map<String, String> validation) {
        this.code = code;
        this.message = message;
        this.validation = validation;
    }

    public void addValidation(final String fieldName, final String errorMessage) {
        this.validation.put(fieldName, errorMessage);
    }

}
