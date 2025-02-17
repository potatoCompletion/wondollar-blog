package com.wondollar.api.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_REQUEST_PARAM(HttpStatus.BAD_REQUEST, "요청 검증 실패"),
    POST_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 글입니다.");

    private final HttpStatus status;
    private final String message;

    public int getStatusCode() {
        return status.value();
    }
}
