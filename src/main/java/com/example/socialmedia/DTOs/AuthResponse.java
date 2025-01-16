package com.example.socialmedia.DTOs;

import lombok.Data;

@Data
public class AuthResponse {
    private String username;
    private String token;
}