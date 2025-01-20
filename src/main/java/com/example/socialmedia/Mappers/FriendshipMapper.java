package com.example.socialmedia.Mappers;

import org.mapstruct.Mapper;

import com.example.socialmedia.DTOs.FollowFriendshipResponse;
import com.example.socialmedia.Entities.User;

@Mapper(componentModel = "spring")
public interface FriendshipMapper {

    FollowFriendshipResponse toFollowFriendshipResponse(User user);
}
