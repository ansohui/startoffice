package com.example.startoffice.service;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.time.Duration;
import com.example.startoffice.app.dto.BlogDto.BlogGetDto;
@Service
public class NaverBlogCrawlerService {

    public List<BlogGetDto> getBlogPosts(String blogId) {
        List<BlogGetDto> blogPosts = new ArrayList<>();

        // Chrome 옵션 설정
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
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

            // XPath로 <a class="title"> 요소들 찾기
            List<WebElement> elements = wait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(
                            By.xpath("//a[contains(@class, 'title')]")
                    )
            );

            System.out.println("✅ 가져온 게시글 개수: " + elements.size());

            for (WebElement element : elements) {
                String title = element.getText().trim();
                String link = element.getAttribute("href").trim();
                blogPosts.add(new BlogGetDto(title,link));
            }

        } catch (Exception e) {
            System.out.println("🚨 크롤링 중 오류 발생: " + e.getMessage());
        } finally {
            driver.quit();
        }

        return blogPosts;
    }
}
