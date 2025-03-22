package com.example.startoffice.app.dto;
import lombok.*;

import java.time.LocalDateTime;

public class NoticeDto {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NoticeRequestDto{
        private String title;
        private String content;

    }
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NoticeGetDto{
        private LocalDateTime createdAt;
        private String title;
        private String content;

    }
}
