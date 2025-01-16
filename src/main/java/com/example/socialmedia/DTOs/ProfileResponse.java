package com.example.socialmedia.DTOs;

import lombok.Data;

@Data
public class ProfileResponse {

    private String firstName;
    private String lastName;
    private String bio;
    private String profilePictureUrl;
    private String coverPictureUrl;
}
