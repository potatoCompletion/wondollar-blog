package com.wondollar.api.post.controller;

import com.wondollar.api.post.request.PostCreate;
import com.wondollar.api.post.response.PostResponse;
import com.wondollar.api.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity post(@RequestBody @Valid PostCreate request) {
        Long id = postService.write(request);
        return ResponseEntity.created(URI.create("/posts/" + id)).build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> get(@PathVariable(name = "postId") Long id) {
        PostResponse response = postService.getPostById(id);
        return ResponseEntity.ok(response);
    }
}
