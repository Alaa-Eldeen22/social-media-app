package com.example.socialmedia.DTOs;

import lombok.Data;

@Data
public class FollowerResponse {

    public FollowerResponse(String username, String profilePictureUrl) {
        this.username = username;
        this.profilePictureUrl = profilePictureUrl;
    }

    private String username;
    private String profilePictureUrl;
}
