package com.example.socialmedia.Services;

import java.util.List;

import com.example.socialmedia.DTOs.FollowFriendshipResponse;

public interface IFollowService {

    /**
     * Follows a user identified by their username.
     *
     * @param followedUsername the username of the user to follow.
     */
    void followUser(String followedUsername);

    /**
     * Unfollows a user identified by their username.
     *
     * @param followedUsername the username of the user to unfollow.
     */
    void unfollowUser(String followedUsername);

    /**
     * Retrieves a list of users who follow the authenticated user.
     *
     * @return a list of follower responses.
     */
    List<FollowFriendshipResponse> getFollowers();

    /**
     * Retrieves a list of users that the authenticated user is following.
     *
     * @return a list of following responses.
     */
    List<FollowFriendshipResponse> getFollowing();
}
