FROM eclipse-temurin:17-jdk

# 필요한 패키지 설치
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
    && apt-get clean

# Google Chrome 설치
RUN wget -q -O google-chrome.deb https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb && \
    apt-get install -y ./google-chrome.deb && \
    rm google-chrome.deb

# ChromeDriver 설치 (버전 자동 매칭)
RUN CHROME_VERSION=$(google-chrome --version | grep -oP '\d+\.\d+\.\d+') && \
    CHROMEDRIVER_VERSION=$(curl -s "https://googlechromelabs.github.io/chrome-for-testing/last-known-good-versions-with-downloads.json" | grep -A10 "$CHROME_VERSION" | grep "linux64" | grep "chromedriver" | grep -o 'https://[^"]*') && \
    wget -q -O chromedriver.zip "$CHROMEDRIVER_VERSION" && \
    unzip chromedriver.zip && \
    mv chromedriver /usr/local/bin/chromedriver && \
    chmod +x /usr/local/bin/chromedriver && \
    rm chromedriver.zip

# 환경 변수
ENV CHROME_BIN=/usr/bin/google-chrome
ENV CHROMEDRIVER_PATH=/usr/local/bin/chromedriver

WORKDIR /app

COPY . .
RUN ./gradlew build -x test

EXPOSE 8080
CMD ["java", "-jar", "build/libs/startoffice-0.0.1-SNAPSHOT.jar"]
