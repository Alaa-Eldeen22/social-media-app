package com.example.socialmedia.Services;

import java.util.List;

import com.example.socialmedia.DTOs.FollowFriendshipResponse;

public interface IFriendshipService {

    /**
     * Sends a friend request to a specified user.
     *
     * @param receiverUsername the username of the user to whom the friend request
     *                         is sent.
     */
    void sendFriendRequest(String receiverUsername);

    /**
     * Accepts a pending friend request from a specific user.
     *
     * @param requesterUsername the username of the user whose friend request is
     *                          accepted.
     */
    void acceptFriendRequest(String requesterUsername);

    /**
     * Rejects a pending friend request from a specific user.
     *
     * @param requesterUsername the username of the user whose friend request is
     *                          rejected.
     */
    void rejectFriendRequest(String requesterUsername);

    /**
     * Removes a user from the authenticated user's friend list.
     *
     * @param usernameToUnfriend the username of the user to unfriend.
     */
    void unfriend(String usernameToUnfriend);

    /**
     * Retrieves a list of pending friend requests sent by the authenticated user.
     *
     * @return a list of responses representing sent friend requests.
     */
    List<FollowFriendshipResponse> getSentFriendRequests();

    /**
     * Retrieves a list of pending friend requests received by the authenticated
     * user.
     *
     * @return a list of responses representing received friend requests.
     */
    List<FollowFriendshipResponse> getReceivedFriendRequests();

    /**
     * Retrieves the list of friends of the authenticated user.
     *
     * @return a list of responses representing friends.
     */
    List<FollowFriendshipResponse> getFriends();
}
