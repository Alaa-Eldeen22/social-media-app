package com.example.socialmedia.DTOs;

import lombok.Data;

@Data
public class UpdatePostRequest {
    private String content;
    private String imageUrl;
}