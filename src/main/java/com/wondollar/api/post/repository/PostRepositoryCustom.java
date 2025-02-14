package com.wondollar.api.post.repository;

import com.wondollar.api.post.domain.Post;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> findPosts(int page);
}
