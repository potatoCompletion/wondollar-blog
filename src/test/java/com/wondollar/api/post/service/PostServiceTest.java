package com.wondollar.api.post.service;

import com.wondollar.api.post.domain.Post;
import com.wondollar.api.post.repository.PostRepository;
import com.wondollar.api.post.request.PostCreateRequest;
import com.wondollar.api.post.response.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    void 글_작성() {
        // given
        PostCreateRequest postCreateRequest = PostCreateRequest.builder()
                .title("제목입니다")
                .content("내용입니다")
                .build();

        // when
        postService.write(postCreateRequest);
        Post post = postRepository.findAll().get(0);

        // then
        assertEquals(1L, postRepository.count());
        assertEquals("제목입니다", post.getTitle());
        assertEquals("내용입니다", post.getContent());
    }

    @Test
    void 글_조회() {
        // given
        Post post = Post.builder()
                .title("제목입니다")
                .content("내용입니다")
                .build();
        postRepository.save(post);

        // when
        PostResponse foundPost = postService.getPostById(post.getId());

        // then
        assertEquals("제목입니다", foundPost.title());
        assertEquals("내용입니다", foundPost.content());
    }

    @Test
    void 글_여러개_조회() {
        // given
        for (int i = 0; i < 50; i++) {
            Post post = Post.builder()
                    .title("제목입니다")
                    .content("내용입니다")
                    .build();
            postRepository.save(post);
        }

        // when
        List<PostResponse> postResponseList = postService.getPosts();

        // then
        assertEquals(50, postResponseList.size());
    }
}