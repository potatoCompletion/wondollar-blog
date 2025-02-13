package com.wondollar.api.post.response;

import com.wondollar.api.post.domain.Post;
import lombok.Builder;

@Builder
public record PostResponse(String title, String content) {

    public static PostResponse fromPost(Post post) {
        return PostResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }
}
