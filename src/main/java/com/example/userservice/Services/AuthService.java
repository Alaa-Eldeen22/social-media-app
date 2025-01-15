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
import com.example.userservice.Utils.RandomUsername;

@Service
public class AuthService {

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
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        AuthResponse response = new AuthResponse();

        response.setUsername(user.getEmail());
        response.setToken(token);
        return response;
    }
}
