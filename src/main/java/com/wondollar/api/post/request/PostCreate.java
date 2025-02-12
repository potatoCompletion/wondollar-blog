package com.wondollar.api.post.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class PostCreate {

    @NotBlank(message = "title 값은 필수입니다.")
    private String title;

    @NotBlank(message = "content 값은 필수입니다.")
    private String content;
}
