package com.example.socialmedia.Services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.socialmedia.DTOs.FollowFriendshipResponse;
import com.example.socialmedia.Entities.Follow;
import com.example.socialmedia.Entities.User;
import com.example.socialmedia.Mappers.FriendshipMapper;
import com.example.socialmedia.Repositories.FollowRepository;
import com.example.socialmedia.Services.IFollowService;
import com.example.socialmedia.Utils.UserUtils;

@Service
public class FollowService implements IFollowService {

    private final FollowRepository followRepository;
    private final UserUtils userUtils;

    // Constructor injection
    public FollowService(FollowRepository followRepository, UserUtils userUtils) {
        this.followRepository = followRepository;
        this.userUtils = userUtils;
    }

    public void followUser(String followedUsername) {
        User follower = userUtils.getAuthenticatedUser();
        User followed = userUtils.getUserByUsername(followedUsername);

        // Check if the relationship already exists
        if (!followRepository.existsByFollowerAndFollowed(follower, followed)) {
            Follow follow = new Follow();
            follow.setFollower(follower);
            follow.setFollowed(followed);
            followRepository.save(follow);
        }
    }

    public void unfollowUser(String followedUsername) {
        User follower = userUtils.getAuthenticatedUser();
        User followed = userUtils.getUserByUsername(followedUsername);

        followRepository.deleteByFollowerAndFollowed(follower, followed);
    }

    // Get followers of a user
    public List<FollowFriendshipResponse> getFollowers() {
        User user = userUtils.getAuthenticatedUser();

        return followRepository.findByFollowed(user).stream()
                .map(follow -> FriendshipMapper.toFollowFriendshipResponse(follow.getFollower()))
                .toList();
    }

    // Get users that a user is following
    public List<FollowFriendshipResponse> getFollowing() {
        User user = userUtils.getAuthenticatedUser();

        return followRepository.findByFollower(user).stream()
                .map(follow -> FriendshipMapper.toFollowFriendshipResponse(follow.getFollowed()))
                .toList();
    }
}
