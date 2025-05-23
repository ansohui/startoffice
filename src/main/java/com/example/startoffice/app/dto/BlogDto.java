package com.example.startoffice.app.dto;
import lombok.*;


public class BlogDto {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BlogGetDto{
        private String title;
        private String url;

    }
}

