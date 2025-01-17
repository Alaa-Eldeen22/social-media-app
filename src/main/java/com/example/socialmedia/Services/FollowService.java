package com.example.socialmedia.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.socialmedia.DTOs.FollowerResponse;
import com.example.socialmedia.Entities.Follow;
import com.example.socialmedia.Entities.User;
import com.example.socialmedia.Repositories.FollowRepository;
import com.example.socialmedia.Utils.UserUtils;

@Service
public class FollowService {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserUtils userUtils;

    // Follow a user
    public void followUser(String followerUsername, String followedUsername) {
        User follower = userUtils.getUserByUsername(followerUsername);
        User followed = userUtils.getUserByUsername(followedUsername);

        // Check if the relationship already exists
        if (!followRepository.existsByFollowerAndFollowed(follower, followed)) {
            Follow follow = new Follow();
            follow.setFollower(follower);
            follow.setFollowed(followed);
            followRepository.save(follow);
        }
    }

    // Unfollow a user
    public void unfollowUser(String followerUsername, String followedUsername) {
        User follower = userUtils.getUserByUsername(followerUsername);
        User followed = userUtils.getUserByUsername(followedUsername);

        followRepository.deleteByFollowerAndFollowed(follower, followed);
    }

    // Get followers of a user
    public List<FollowerResponse> getFollowers(String username) {
        User user = userUtils.getUserByUsername(username);

        return followRepository.findByFollowed(user).stream().map(
                follow -> new FollowerResponse(
                        follow.getFollower().getUsername(), follow.getFollower().getProfilePictureUrl()))
                .toList();

    }

    // Get users that a user is following
    public List<FollowerResponse> getFollowing(String username) {
        User user = userUtils.getUserByUsername(username);

        return followRepository.findByFollower(user).stream().map(
                follow -> new FollowerResponse(
                        follow.getFollowed().getUsername(), follow.getFollower().getProfilePictureUrl()))
                .toList();

    }
}