package com.example.socialmedia.Services;

import com.example.socialmedia.DTOs.AuthResponse;
import com.example.socialmedia.DTOs.LoginRequest;
import com.example.socialmedia.DTOs.RegisterRequest;

public interface IAuthService {

    /**
     * Registers a new user with the provided registration details.
     *
     * @param registerRequest the details of the user to register.
     * @return a response containing the username and JWT token.
     * @throws RuntimeException if the email already exists.
     */
    AuthResponse register(RegisterRequest registerRequest);

    /**
     * Authenticates a user using their email and password.
     *
     * @param loginRequest the login credentials.
     * @return a response containing the username and JWT token.
     * @throws RuntimeException      if the password is invalid.
     * @throws UserNotFoundException if the user is not found.
     */
    AuthResponse login(LoginRequest loginRequest);
}
