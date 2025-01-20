package com.example.socialmedia.DTOs;

import lombok.Data;

@Data
public class FollowFriendshipResponse {

    private String firstName;
    private String lastName;
    private String username;
    private String profilePictureUrl;
}
