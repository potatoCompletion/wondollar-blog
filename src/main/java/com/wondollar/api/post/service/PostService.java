package com.wondollar.api.post.service;

import com.wondollar.api.post.domain.Post;
import com.wondollar.api.post.repository.PostRepository;
import com.wondollar.api.post.request.PostCreateRequest;
import com.wondollar.api.post.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Long write(PostCreateRequest postCreateRequest) {
        Post post = postCreateRequest.toPost();
        return postRepository.save(post).getId();
    }

    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        return PostResponse.fromPost(post);
    }

    public List<PostResponse> getPosts(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return postRepository.findAll(pageable)
                .stream()
                .map(post -> PostResponse.fromPost(post))
                .collect(Collectors.toList());
    }
}
