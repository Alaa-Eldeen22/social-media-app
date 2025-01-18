package com.example.socialmedia.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.socialmedia.DTOs.FollowFriendshipResponse;
import com.example.socialmedia.Entities.Friendship;
import com.example.socialmedia.Entities.User;
import com.example.socialmedia.Enums.FriendshipStatus;
import com.example.socialmedia.Exception.FriendshipException;
import com.example.socialmedia.Repositories.FriendshipRepository;
import com.example.socialmedia.Utils.UserUtils;

@Service
public class FriendShipService {

    @Autowired
    FriendshipRepository friendshipRepository;

    @Autowired
    UserUtils userUtils;

    // Send a friend request
    public ResponseEntity<String> sendFriendRequest(String receiverUsername) {

        String requesterUsername = userUtils.getAuthenticatedUsername();

        User receiver = userUtils.getUserByUsername(receiverUsername);
        User requester = userUtils.getUserByUsername(requesterUsername);

        // Validate that the requester and receiver are not the same user
        if (requester.equals(receiver)) {
            throw new FriendshipException("You cannot send a friend request to yourself");
        }

        // Check if a friendship already exists between the two users
        boolean friendshipExists = friendshipRepository.existsByRequesterAndReceiver(requester, receiver)
                || friendshipRepository.existsByRequesterAndReceiver(receiver, requester);

        if (friendshipExists) {
            throw new FriendshipException("A friend request or friendship already exists between these users");
        }

        // Create and save the friendship request
        Friendship friendship = new Friendship();
        friendship.setRequester(requester);
        friendship.setReceiver(receiver);
        friendship.setStatus(FriendshipStatus.PENDING);
        friendshipRepository.save(friendship);

        return ResponseEntity.ok("Friend request sent successfully");
    }

    // Accept a friend request
    public ResponseEntity<String> acceptFriendRequest(String requesterUsername) {

        String receiverUsername = userUtils.getAuthenticatedUsername();

        User receiver = userUtils.getUserByUsername(receiverUsername);
        User requester = userUtils.getUserByUsername(requesterUsername);

        // Fetch the friendship record
        Friendship friendship = friendshipRepository.findByRequesterAndReceiver(requester, receiver)
                .orElseThrow(() -> new FriendshipException("No friend request found from this user"));

        // Ensure the friendship is not already accepted
        if (friendship.getStatus() == FriendshipStatus.ACCEPTED) {
            throw new FriendshipException("This friend request is already accepted");
        }

        // Update the friendship status to ACCEPTED
        friendship.setStatus(FriendshipStatus.ACCEPTED);
        friendshipRepository.save(friendship);

        return ResponseEntity.ok("Friend request accepted successfully");
    }

    // Reject a friend request
    public ResponseEntity<String> rejectFriendRequest(String requesterUsername) {

        String receiverUsername = userUtils.getAuthenticatedUsername();

        User receiver = userUtils.getUserByUsername(receiverUsername);
        User requester = userUtils.getUserByUsername(requesterUsername);

        // Fetch the friendship record
        Friendship friendship = friendshipRepository.findByRequesterAndReceiver(requester, receiver)
                .orElseThrow(() -> new FriendshipException("No pending friend request from this user"));

        // Ensure the friendship status is PENDING
        if (friendship.getStatus() != FriendshipStatus.PENDING) {
            throw new FriendshipException("You can't reject a request that is not pending");
        }

        friendshipRepository.delete(friendship);

        return ResponseEntity.ok("Request accepted successfully");
    }

    // Unfriend a user
    public ResponseEntity<String> unfriend(String usernameToUnfriend) {

        String requesterUsername = userUtils.getAuthenticatedUsername();

        User userToUnfriend = userUtils.getUserByUsername(usernameToUnfriend);
        User requester = userUtils.getUserByUsername(requesterUsername);

        // Check if there is an existing friendship relationship

        Friendship friendship = friendshipRepository.findByRequesterAndReceiver(requester, userToUnfriend)
                .orElse(friendshipRepository.findByRequesterAndReceiver(userToUnfriend, requester)
                        .orElseThrow(() -> new FriendshipException("No friendship exists between these users")));

        // Ensure the friendship status is not PENDING

        if (friendship.getStatus() == FriendshipStatus.PENDING) {
            throw new FriendshipException("You cannot unfriend a user while the friendship is still pending");
        }

        friendshipRepository.delete(friendship);

        return ResponseEntity.ok("Successfully unfriended the user");
    }

    // Get pending friend requests sent by a user
    public List<FollowFriendshipResponse> getSentFriendRequests(String username) {
        User user = userUtils.getUserByUsername(username);
        return friendshipRepository.findByRequesterAndStatus(user, FriendshipStatus.PENDING).stream()
                .map(friendship -> mapToFriendsResponse(friendship.getReceiver())).toList();
    }

    // Get pending friend requests received by a user
    public List<FollowFriendshipResponse> getReceivedFriendRequests(String username) {

        User user = userUtils.getUserByUsername(username);

        return friendshipRepository.findByReceiverAndStatus(user, FriendshipStatus.PENDING).stream()
                .map(friendship -> mapToFriendsResponse(friendship.getRequester())).toList();
    }

    // Helper method to map User to FollowerResponse
    private FollowFriendshipResponse mapToFriendsResponse(User user) {
        return new FollowFriendshipResponse(user.getFirstName(), user.getLastName(), user.getUsername(),
                user.getProfilePictureUrl());
    }
}
