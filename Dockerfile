# 1. Ubuntu 22.04 이미지 사용
FROM ubuntu:22.04

# 2. 비대화형 모드 설정 (설치 도중 프롬프트 방지)
ENV DEBIAN_FRONTEND=noninteractive

# 3. 필수 패키지 설치
RUN apt-get update && apt-get install -y \
    wget \
    curl \
    unzip \
    ca-certificates \
    gnupg \
    openjdk-17-jdk \
    libnss3 \
    libxss1 \
    libasound2 \
    libgconf-2-4 \
    libgbm1 \
    xdg-utils && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# 4. Google Chrome 설치 (.deb로 설치 시 의존성 해결 필요)
RUN wget -q -O google-chrome.deb https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb && \
    dpkg -i google-chrome.deb || apt-get install -f -y && \
    rm google-chrome.deb

# 5. ChromeDriver 설치 (버전 고정, 공식 저장소 사용)
RUN CHROME_DRIVER_VERSION=124.0.6367.91 && \
    wget -q -O /tmp/chromedriver.zip https://storage.googleapis.com/chrome-for-testing-public/$CHROME_DRIVER_VERSION/linux64/chromedriver-linux64.zip && \
    unzip /tmp/chromedriver.zip -d /tmp && \
    mv /tmp/chromedriver-linux64/chromedriver /usr/local/bin/chromedriver && \
    chmod +x /usr/local/bin/chromedriver && \
    rm -rf /tmp/*

# 6. 환경 변수 설정 (Selenium 자동 인식용)
ENV CHROME_BIN=/usr/bin/google-chrome
ENV CHROMEDRIVER_PATH=/usr/local/bin/chromedriver

# 7. 앱 작업 디렉토리 설정
WORKDIR /app

# 8. 프로젝트 복사 및 빌드
COPY . .
RUN ./gradlew build -x test

# 9. Chromedriver 설치 확인용 로그
RUN ls -l /usr/local/bin/chromedriver || echo "❌ chromedriver 설치 실패"

# 10. Spring Boot 앱 포트 열기
EXPOSE 8080

# 11. 앱 실행 명령
CMD ["java", "-jar", "build/libs/startoffice-0.0.1-SNAPSHOT.jar"]
