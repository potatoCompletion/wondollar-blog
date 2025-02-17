package com.wondollar.api.common.exception.customexception;

import com.wondollar.api.common.exception.ErrorCode;

public class PostNotFoundException extends CustomException {

    public PostNotFoundException() {
        super(ErrorCode.POST_NOT_FOUND);
    }
}
