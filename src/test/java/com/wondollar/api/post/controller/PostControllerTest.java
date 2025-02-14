package com.wondollar.api.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wondollar.api.post.domain.Post;
import com.wondollar.api.post.repository.PostRepository;
import com.wondollar.api.post.request.PostCreateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    void test() throws Exception {
        // given
        PostCreateRequest request = PostCreateRequest.builder()
                .title("제목입니다")
                .content("내용입니다")
                .build();
        String jsonRequest = objectMapper.writeValueAsString(request);

        System.out.println(jsonRequest);

        // expected
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(APPLICATION_JSON)
                        .content(jsonRequest)
                )
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void post_요청시_title_content_값은_필수다() throws Exception {
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(APPLICATION_JSON)
                        .content("{\"title\":\"\", \"content\":\"\"}")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("400"))
                .andExpect(jsonPath("$.errorCode").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("요청 검증 실패"))
                .andDo(print());
    }

    @Test
    void post_저장_테스트() throws Exception {
        // given
        PostCreateRequest request = PostCreateRequest.builder()
                .title("제목입니다")
                .content("내용입니다")
                .build();
        String jsonRequest = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(APPLICATION_JSON)
                        .content(jsonRequest)
                )
                .andExpect(status().isCreated())
                .andDo(print());

        //then
        assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);

        assertEquals("제목입니다", post.getTitle());
        assertEquals("내용입니다", post.getContent());
    }

    @Test
    void post_단건조회_테스트() throws Exception {
        // given
        PostCreateRequest request = PostCreateRequest.builder()
                .title("제목입니다")
                .content("내용입니다")
                .build();
        String jsonRequest = objectMapper.writeValueAsString(request);

        // when
        MvcResult postResult = mockMvc.perform(post("/api/v1/posts")
                        .contentType(APPLICATION_JSON)
                        .content(jsonRequest)
                )
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        String postLocation = postResult.getResponse().getHeader("location");

        // then
        mockMvc.perform(get("/api/v1" + postLocation))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("제목입니다"))
                .andExpect(jsonPath("$.content").value("내용입니다"))
                .andDo(print());
    }

    @Test
    void post_여러개조회_테스트() throws Exception {
        // given
        Post post1 = Post.builder()
                .title("1")
                .content("1")
                .build();
        postRepository.save(post1);

        Post post2 = Post.builder()
                .title("2")
                .content("2")
                .build();
        postRepository.save(post2);

        // expected
        mockMvc.perform(get("/api/v1/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].id").value(post1.getId()))
                .andExpect(jsonPath("$[0].title").value(post1.getTitle()))
                .andExpect(jsonPath("$[0].content").value(post1.getContent()))
                .andExpect(jsonPath("$[1].id").value(post2.getId()))
                .andExpect(jsonPath("$[1].title").value(post2.getTitle()))
                .andExpect(jsonPath("$[1].content").value(post2.getContent()))
                .andDo(print());
    }
}