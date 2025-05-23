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

@Service
public class NaverBlogCrawlerService {

    private static final String BLOG_URL = "https://m.blog.naver.com/";

    public List<String> getBlogPosts(String blogId) {
        List<String> blogPosts = new ArrayList<>();

        // Chrome WebDriver 설정
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");

        // Chrome 옵션 설정
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); // Headless 모드 비활성화
        options.addArguments("--disable-gpu", "--no-sandbox", "--disable-dev-shm-usage");
        options.addArguments("user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
            String url = BLOG_URL + blogId;
            System.out.println("크롤링할 URL: " + url);
            driver.get(url);

            // ✅ JavaScript 로딩 대기
            new WebDriverWait(driver, Duration.ofSeconds(20)).until(
                    webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete")
            );

            // ✅ 페이지 소스 확인
            System.out.println(driver.getPageSource());

            // ✅ XPath 방식으로 제목 가져오기
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[contains(@class, 'title__')]")));
            List<WebElement> elements = driver.findElements(By.xpath("//strong[contains(@class, 'title__')]"));

            System.out.println("✅ 가져온 게시글 개수: " + elements.size());

            if (elements.isEmpty()) {
                System.out.println("🚨 게시글을 찾을 수 없음! 네이버가 차단했거나 HTML 구조가 변경됨.");
            }

            for (WebElement element : elements) {
                String title = element.getText();  // 글 제목
                blogPosts.add(title);
            }

        } catch (Exception e) {
            System.out.println("🚨 크롤링 중 오류 발생: " + e.getMessage());
        } finally {
            driver.quit();
        }

        return blogPosts;
    }
}
