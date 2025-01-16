package com.example.userservice.DTOs;

import lombok.Data;

@Data
public class AuthResponse {
    private String username;
    private String token;
}