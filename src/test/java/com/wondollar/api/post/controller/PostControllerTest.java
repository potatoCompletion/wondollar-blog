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

import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    void post_페이지조회_테스트() throws Exception {
        // given
        postRepository.saveAll(IntStream.range(1, 31)
                .mapToObj(i -> Post.builder()
                        .title("제목 - " + i)
                        .content("내용 - " + i)
                        .build())
                .collect(Collectors.toList()));

        // expected
        // page 1
        mockMvc.perform(get("/api/v1/posts?page=1&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size", is(10)))
                .andExpect(jsonPath("$.content[0].title").value("제목 - 30"))
                .andExpect(jsonPath("$.content[0].content").value("내용 - 30"))
                .andDo(print());

        // page 2
        mockMvc.perform(get("/api/v1/posts?page=2&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size", is(10)))
                .andExpect(jsonPath("$.content[0].title").value("제목 - 20"))
                .andExpect(jsonPath("$.content[0].content").value("내용 - 20"))
                .andDo(print());
    }

    @Test
    void post_페이지_0_조회_테스트() throws Exception {
        // given
        postRepository.saveAll(IntStream.range(1, 31)
                .mapToObj(i -> Post.builder()
                        .title("제목 - " + i)
                        .content("내용 - " + i)
                        .build())
                .collect(Collectors.toList()));

        // expected
        // page 1
        mockMvc.perform(get("/api/v1/posts?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size", is(10)))
                .andExpect(jsonPath("$.content[0].title").value("제목 - 30"))
                .andExpect(jsonPath("$.content[0].content").value("내용 - 30"))
                .andDo(print());
    }

    @Test
    void post_존재하지_않는_글_조회_테스트() throws Exception {

        // expected
        mockMvc.perform(get("/api/v1/posts/1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("400"))
                .andExpect(jsonPath("$.errorCode").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("존재하지 않는 글입니다."))
                .andDo(print());
    }
}