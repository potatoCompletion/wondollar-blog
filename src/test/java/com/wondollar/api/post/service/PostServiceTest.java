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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    void 글_1페이지_조회() {
        // given
        List<Post> requestPosts = IntStream.range(0, 30)
                .mapToObj(i -> Post.builder()
                        .title("제목 - " + i)
                        .content("내용 - " + i)
                        .build())
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        // when
        List<PostResponse> postResponseList = postService.getPosts(0);

        // then
        assertEquals(5, postResponseList.size());
        assertEquals("제목 - 0", postResponseList.get(0).title());
        assertEquals("내용 - 0", postResponseList.get(0).content());
    }
}