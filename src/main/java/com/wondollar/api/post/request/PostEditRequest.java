package com.wondollar.api.post.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostEditRequest {

    @NotBlank(message = "title 값은 필수입니다.")
    private String title;

    @NotBlank(message = "content 값은 필수입니다.")
    private String content;

    @Builder
    public PostEditRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
