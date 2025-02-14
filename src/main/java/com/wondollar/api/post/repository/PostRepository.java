package com.wondollar.api.post.repository;

import com.wondollar.api.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository <Post, Long>, PostRepositoryCustom {
}
