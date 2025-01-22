package com.example.socialmedia.DTOs;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CommentResponse {
    private Long id;
    private String content;
    private String username;
    private Long postId;
    private LocalDateTime createdAt;
}