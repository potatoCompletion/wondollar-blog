package com.wondollar.api.common.exception.customexception;

import com.wondollar.api.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public abstract class CustomException extends RuntimeException{

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
