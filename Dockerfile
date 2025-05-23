FROM ubuntu:22.04

ENV DEBIAN_FRONTEND=noninteractive

# 1. 필수 패키지 설치
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
    xdg-utils \
    fonts-liberation \
    libappindicator3-1 \
    libatk-bridge2.0-0 \
    libgtk-3-0 \
    libdrm2 \
    && apt-get clean && rm -rf /var/lib/apt/lists/*

# 2. Chrome 124 설치
RUN wget https://dl.google.com/linux/chrome/deb/pool/main/g/google-chrome-stable/google-chrome-stable_124.0.6367.91-1_amd64.deb && \
    apt-get install -y ./google-chrome-stable_124.0.6367.91-1_amd64.deb && \
    rm google-chrome-stable_124.0.6367.91-1_amd64.deb

# 3. ChromeDriver 124 설치
RUN CHROME_DRIVER_VERSION=124.0.6367.91 && \
    wget -q -O /tmp/chromedriver.zip https://edgedl.me.gvt1.com/edgedl/chrome/chrome-for-testing/$CHROME_DRIVER_VERSION/linux64/chromedriver-linux64.zip && \
    unzip /tmp/chromedriver.zip -d /tmp && \
    mv /tmp/chromedriver-linux64/chromedriver /usr/local/bin/chromedriver && \
    chmod +x /usr/local/bin/chromedriver && \
    rm -rf /tmp/*

ENV CHROME_BIN=/usr/bin/google-chrome
ENV CHROMEDRIVER_PATH=/usr/local/bin/chromedriver

WORKDIR /app
COPY . .
RUN ./gradlew build -x test

EXPOSE 8080
CMD ["java", "-jar", "build/libs/startoffice-0.0.1-SNAPSHOT.jar"]
