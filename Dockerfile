FROM eclipse-temurin:17-jdk

# 필수 도구 및 안정된 Chrome 실행 환경만 설치
RUN apt-get update && apt-get install -y \
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
    && apt-get clean

# Google Chrome 설치
RUN wget -q -O google-chrome.deb https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb && \
    apt-get install -y ./google-chrome.deb && \
    rm google-chrome.deb

# ChromeDriver 설치
RUN CHROME_DRIVER_VERSION=124.0.6367.91 && \
    wget -q -O /tmp/chromedriver.zip https://storage.googleapis.com/chrome-for-testing-public/$CHROME_DRIVER_VERSION/linux64/chromedriver-linux64.zip && \
    unzip /tmp/chromedriver.zip -d /tmp && \
    mv /tmp/chromedriver-linux64/chromedriver /usr/local/bin/chromedriver && \
    chmod +x /usr/local/bin/chromedriver && \
    rm -rf /tmp/*

ENV CHROME_BIN=/usr/bin/google-chrome
ENV CHROMEDRIVER_PATH=/usr/local/bin/chromedriver

WORKDIR /app

COPY . .
RUN ./gradlew build -x test

RUN ls -l /usr/local/bin/chromedriver || echo "❌ chromedriver 설치 실패"

EXPOSE 8080
CMD ["java", "-jar", "build/libs/startoffice-0.0.1-SNAPSHOT.jar"]
