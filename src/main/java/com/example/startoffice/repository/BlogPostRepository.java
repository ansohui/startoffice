package com.example.startoffice.repository;

import com.example.startoffice.domain.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    List<BlogPost> findByBlogId(String blogId);
    void deleteByBlogId(String blogId);
}
