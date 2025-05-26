package com.example.startoffice.service;

import com.example.startoffice.repository.BlogPostRepository;
import com.example.startoffice.domain.BlogPost;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.time.Duration;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import com.example.startoffice.app.dto.BlogDto.BlogGetDto;

@Service
public class NaverBlogCrawlerService {
    private final BlogPostRepository blogPostRepository;

    public NaverBlogCrawlerService(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }


    public List<BlogGetDto> getBlogPosts(String blogId) {
        List<BlogPost> existingPosts = blogPostRepository.findByBlogId(blogId);

        // 24시간 내 저장된 데이터 있으면 DB에서 반환
        long now = Instant.now().toEpochMilli();
        if (!existingPosts.isEmpty() && now - existingPosts.get(0).getCrawledAt() < Duration.ofHours(24).toMillis()) {
            return existingPosts.stream()
                    .map(p -> new BlogGetDto(p.getTitle(), p.getUrl()))
                    .collect(Collectors.toList());
        }
            List<BlogGetDto> blogPosts = new ArrayList<>();

            // Chrome 옵션 설정
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless=chrome");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("user-agent=Mozilla/5.0");

            WebDriver driver = new ChromeDriver(options);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            try {
                String url = "https://blog.naver.com/" + blogId;
                System.out.println("크롤링할 URL: " + url);
                driver.get(url);

                // iframe으로 전환
                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("mainFrame"));

                // XPath로 <a class="pcol2"> 요소 찾기
                List<WebElement> elements = wait.until(
                        ExpectedConditions.presenceOfAllElementsLocatedBy(
                                By.xpath("//a[@class='pcol2']")
                        )
                );

                System.out.println("✅ 가져온 게시글 개수: " + elements.size());

                for (WebElement element : elements) {
                    String title = element.getText().trim();
                    String relativeUrl = element.getAttribute("href").trim();


                    blogPosts.add(new BlogGetDto(title, relativeUrl));
                }

            } catch (Exception e) {
                System.out.println("🚨 크롤링 중 오류 발생: " + e.getMessage());
            } finally {
                driver.quit();
            }

            return blogPosts;
        }
    }
