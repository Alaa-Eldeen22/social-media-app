package com.example.userservice.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.userservice.DTOs.AuthResponse;
import com.example.userservice.DTOs.LoginRequest;
import com.example.userservice.DTOs.RegisterRequest;
import com.example.userservice.Entities.User;
import com.example.userservice.Repositories.UserRepository;
import com.example.userservice.Utils.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail());

        AuthResponse response = new AuthResponse();
        response.setEmail(user.getEmail());
        response.setToken(token);
        return response;
    }

    public AuthResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        AuthResponse response = new AuthResponse();
        response.setEmail(user.getEmail());
        response.setToken(token);
        return response;
    }
}
