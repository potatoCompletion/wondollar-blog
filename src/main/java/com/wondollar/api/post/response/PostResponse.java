package com.wondollar.api.post.response;

import com.wondollar.api.post.domain.Post;
import lombok.Builder;

@Builder
public record PostResponse(Long id, String title, String content) {

    public static PostResponse fromPost(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }
}
