package com.wondollar.api.post.request;

import com.wondollar.api.post.domain.Post;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class PostCreateRequest {

    @NotBlank(message = "title 값은 필수입니다.")
    private String title;

    @NotBlank(message = "content 값은 필수입니다.")
    private String content;

    @Builder
    public PostCreateRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Post toPost() {
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }
}
