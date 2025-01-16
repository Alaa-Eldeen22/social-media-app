package com.example.socialmedia.DTOs;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PostResponse {
    private Long id;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;
    private String username;
}
