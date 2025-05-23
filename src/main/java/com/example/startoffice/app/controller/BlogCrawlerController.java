package com.example.startoffice.app.controller;

import com.example.startoffice.app.dto.BlogDto;
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
    public List<BlogDto.BlogGetDto> getNaverBlogPosts(@RequestParam String blogId) {
        return blogCrawlerService.getBlogPosts(blogId);
    }
}
