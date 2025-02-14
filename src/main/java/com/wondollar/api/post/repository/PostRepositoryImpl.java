package com.wondollar.api.post.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wondollar.api.post.domain.Post;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> findPosts(int page) {
        // TODO
        return null;
    }
}
