package com.example.socialmedia.DTOs;

import lombok.Data;

@Data
public class FollowFriendshipResponse {

    public FollowFriendshipResponse(String username, String profilePictureUrl) {
        this.username = username;
        this.profilePictureUrl = profilePictureUrl;
    }

    public FollowFriendshipResponse(String firstName, String lastName, String username, String profilePictureUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.profilePictureUrl = profilePictureUrl;
    }

    private String firstName;
    private String lastName;
    private String username;
    private String profilePictureUrl;
}
