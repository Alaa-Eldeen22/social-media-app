package com.example.userservice.DTOs;

import lombok.Data;

@Data
public class ProfileResponse {

    private String fisrtName;
    private String lastName;
    private String bio;
    private String profilePictureUrl;
    private String coverPictureUrl;
}
