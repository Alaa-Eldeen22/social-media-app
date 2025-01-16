package com.example.socialmedia.DTOs;

import lombok.Data;

@Data
public class CreatePostRequest {
    private String content;
    private String imageUrl;
}
