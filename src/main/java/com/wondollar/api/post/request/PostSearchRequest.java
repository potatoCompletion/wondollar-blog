package com.wondollar.api.post.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostSearchRequest {

    private int page;
    private int size;

    @Builder
    public PostSearchRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }
}
