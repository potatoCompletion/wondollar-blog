package com.wondollar.api.post.repository;

import com.wondollar.api.post.domain.Post;
import com.wondollar.api.post.request.PostSearchRequest;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> findPosts(PostSearchRequest postSearchRequest);
}
