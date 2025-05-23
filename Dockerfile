# 1. 베이스 이미지
FROM ubuntu:22.04

# 2. 비대화형 모드 (환경변수로 DEBIAN 프롬프트 제거)
ENV DEBIAN_FRONTEND=noninteractive

# 3. 필수 패키지 설치
RUN apt-get update --allow-releaseinfo-change && \
    apt-get install -y \
        wget \
        curl \
        unzip \
        ca-certificates \
        gnupg \
        libnss3 \
        libgconf-2-4 \
        libxss1 \
        libasound2 \
        libgbm1 \
        xdg-utils \
        openjdk-17-jdk && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# 4. Google Chrome 설치
RUN wget -q -O google-chrome.deb https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb && \
    apt-get install -y ./google-chrome.deb && \
    rm google-chrome.deb

# 5. ChromeDriver 설치
RUN CHROME_DRIVER_VERSION=124.0.6367.91 && \
    wget -q -O /tmp/chromedriver.zip https://storage.googleapis.com/chrome-for-testing-public/$CHROME_DRIVER_VERSION/linux64/chromedriver-linux64.zip && \
    unzip /tmp/chromedriver.zip -d /tmp && \
    mv /tmp/chromedriver-linux64/chromedriver /usr/local/bin/chromedriver && \
    chmod +x /usr/local/bin/chromedriver && \
    rm -rf /tmp/*

# 6. 환경 변수 등록
ENV CHROME_BIN=/usr/bin/google-chrome
ENV CHROMEDRIVER_PATH=/usr/local/bin/chromedriver

# 7. 작업 디렉토리 설정
WORKDIR /app

# 8. 프로젝트 복사 및 빌드
COPY . .
RUN ./gradlew build -x test

# 9. Chromedriver 설치 확인용 로그
RUN ls -l /usr/local/bin/chromedriver || echo "❌ chromedriver 설치 실패"

# 10. 포트 노출
EXPOSE 8080

# 11. 애플리케이션 실행
CMD ["java", "-jar", "build/libs/startoffice-0.0.1-SNAPSHOT.jar"]
