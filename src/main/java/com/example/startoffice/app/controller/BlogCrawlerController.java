package com.example.startoffice.app.controller;

import com.example.startoffice.service.NaverBlogCrawlerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
public class BlogCrawlerController {

    private final NaverBlogCrawlerService blogCrawlerService;

    public BlogCrawlerController(NaverBlogCrawlerService blogCrawlerService) {
        this.blogCrawlerService = blogCrawlerService;
    }

    @GetMapping("/naver")
    public List<String> getNaverBlogPosts(@RequestParam String blogId) {
        return blogCrawlerService.getBlogPosts(blogId);
    }
}
