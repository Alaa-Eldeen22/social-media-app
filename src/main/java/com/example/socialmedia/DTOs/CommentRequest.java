package com.example.socialmedia.DTOs;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentRequest {
    
    @NotBlank(message = "comment can't be empty")
    private String content;
}
