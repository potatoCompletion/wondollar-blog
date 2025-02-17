package com.wondollar.api.common.exception.customexception;

import com.wondollar.api.common.exception.ErrorCode;

public class PostNotFoundException extends CustomException {

    private static final ErrorCode errorCode = ErrorCode.POST_NOT_FOUND;

    public PostNotFoundException() {
        super(errorCode);
    }
}
