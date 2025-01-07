package com.example.userservice.DTOs;

import lombok.Data;

@Data
public class AuthResponse {
    private String email;
    private String token;
}