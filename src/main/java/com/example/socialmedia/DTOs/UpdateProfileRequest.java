package com.example.userservice.DTOs;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateProfileRequest {
    @Size(max = 50, message = "First name must be less than 50 characters")
    private String firstName;

    @Size(max = 50, message = "Last name must be less than 50 characters")
    private String lastName;

    @Size(max = 500, message = "Bio must be less than 500 characters")
    private String bio;

    private String profilePictureUrl;
    private String coverPictureUrl;
}