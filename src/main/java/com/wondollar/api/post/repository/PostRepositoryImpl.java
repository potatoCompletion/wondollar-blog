package com.wondollar.api.post.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wondollar.api.post.domain.Post;
import com.wondollar.api.post.request.PostSearchRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.wondollar.api.post.domain.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> findPosts(PostSearchRequest postSearchRequest) {
        return jpaQueryFactory.selectFrom(post)
                .limit(postSearchRequest.getSize())
                .offset(postSearchRequest.getOffset())
                .orderBy(post.id.desc())
                .fetch();
    }
}
