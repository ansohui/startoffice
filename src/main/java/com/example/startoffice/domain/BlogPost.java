package com.example.startoffice.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String blogId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String url;

    private Long crawledAt; // timestamp로 저장해도 좋음 (epochMillis)
}