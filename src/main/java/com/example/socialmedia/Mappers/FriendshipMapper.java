package com.example.socialmedia.Mappers;

import com.example.socialmedia.DTOs.FollowFriendshipResponse;
import com.example.socialmedia.Entities.User;

public class FriendshipMapper {

    public static FollowFriendshipResponse toFollowFriendshipResponse(User user) {
        if (user == null) {
            return null;
        }
        FollowFriendshipResponse response = new FollowFriendshipResponse();
        response.setUsername(user.getUsername());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setProfilePictureUrl(user.getProfilePictureUrl());
        return response;
    }
}
