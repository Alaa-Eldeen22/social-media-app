package com.example.socialmedia.Services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.socialmedia.DTOs.AuthResponse;
import com.example.socialmedia.DTOs.LoginRequest;
import com.example.socialmedia.DTOs.RegisterRequest;
import com.example.socialmedia.Entities.User;
import com.example.socialmedia.Exception.UserNotFoundException;
import com.example.socialmedia.Repositories.UserRepository;
import com.example.socialmedia.Services.IAuthService;
import com.example.socialmedia.Utils.JwtUtil;
import com.example.socialmedia.Utils.RandomUsername;

@Service
public class AuthService implements IAuthService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RandomUsername randomUsername;

    // @Autowired
    // private AuthResponse response;

    public AuthResponse register(RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        String uniqueUsername = randomUsername.generateUniqueUsername(registerRequest.getFirstName(),
                registerRequest.getLastName());

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setUsername(uniqueUsername);

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getUsername());
        AuthResponse response = new AuthResponse();

        response.setUsername(user.getUsername());
        response.setToken(token);
        return response;
    }

    public AuthResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getUsername());
        AuthResponse response = new AuthResponse();

        response.setUsername(user.getUsername());
        response.setToken(token);
        return response;
    }
}
