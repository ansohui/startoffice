FROM eclipse-temurin:17-jdk

# 필수 패키지 + Chrome + ChromeDriver 설치
RUN apt-get update && apt-get install -y \
    wget \
    curl \
    unzip \
    gnupg \
    libglib2.0-0 \
    libnss3 \
    libgconf-2-4 \
    libfontconfig1 \
    libxss1 \
    libappindicator3-1 \
    libasound2 \
    fonts-liberation \
    libu2f-udev \
    libvulkan1 \
    xdg-utils \
    chromium-browser \
    chromium-driver \
    && apt-get clean

# chromedriver 심볼릭 링크 (표준 경로로 연결)
RUN ln -s /usr/lib/chromium-browser/chromedriver /usr/local/bin/chromedriver

# 환경 변수 설정
ENV CHROME_BIN=/usr/bin/chromium-browser
ENV CHROMEDRIVER_PATH=/usr/local/bin/chromedriver

WORKDIR /app

# 코드 복사 + 빌드
COPY . .
RUN ./gradlew build -x test

# 포트 열기
EXPOSE 8080

# 앱 실행
CMD ["java", "-jar", "build/libs/startoffice-0.0.1-SNAPSHOT.jar"]
