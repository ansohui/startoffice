FROM ubuntu:22.04

ENV DEBIAN_FRONTEND=noninteractive

# 1. 필요한 패키지 설치
RUN apt-get update && apt-get install -y \
    wget \
    curl \
    unzip \
    gnupg \
    ca-certificates \
    libnss3 \
    libgconf-2-4 \
    libxss1 \
    libasound2 \
    libgbm1 \
    libatk-bridge2.0-0 \
    libgtk-3-0 \
    libdrm2 \
    xdg-utils && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# 2. Chrome 124 설치 (Chrome for Testing 버전 사용)
RUN CHROME_VERSION=124.0.6367.91 && \
    wget https://edgedl.me.gvt1.com/edgedl/chrome/chrome-for-testing/$CHROME_VERSION/linux64/chrome-linux64.zip && \
    unzip chrome-linux64.zip && \
    mv chrome-linux64 /opt/chrome && \
    ln -s /opt/chrome/chrome /usr/bin/google-chrome && \
    rm chrome-linux64.zip

# 3. ChromeDriver 124 설치
RUN CHROMEDRIVER_VERSION=124.0.6367.91 && \
    wget https://edgedl.me.gvt1.com/edgedl/chrome/chrome-for-testing/$CHROMEDRIVER_VERSION/linux64/chromedriver-linux64.zip && \
    unzip chromedriver-linux64.zip && \
    mv chromedriver-linux64/chromedriver /usr/local/bin/chromedriver && \
    chmod +x /usr/local/bin/chromedriver && \
    rm chromedriver-linux64.zip

ENV CHROME_BIN=/usr/bin/google-chrome
ENV CHROMEDRIVER_PATH=/usr/local/bin/chromedriver

WORKDIR /app
COPY . .
RUN ./gradlew build -x test

EXPOSE 8080
CMD ["java", "-jar", "build/libs/startoffice-0.0.1-SNAPSHOT.jar"]
