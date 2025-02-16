package com.wondollar.api.post.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class PostSearchRequest {

    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10;

    public PostSearchRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public long getOffset() {
        return (long) (Math.max(1, page) - 1) * size;
    }
}
