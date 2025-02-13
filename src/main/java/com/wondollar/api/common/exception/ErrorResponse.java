package com.wondollar.api.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(int status, String errorCode, String message, List<String> errorList) {

    @Builder
    public ErrorResponse(ErrorCode errorCode, List<String> errorList) {
        this(errorCode.getStatus().value(), errorCode.getStatus().name(), errorCode.getMessage(), errorList);
    }
}