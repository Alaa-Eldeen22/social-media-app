package com.example.socialmedia.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.socialmedia.DTOs.FollowFriendshipResponse;
import com.example.socialmedia.Entities.Friendship;
import com.example.socialmedia.Entities.User;
import com.example.socialmedia.Enums.FriendshipStatus;
import com.example.socialmedia.Exception.FriendshipException;
import com.example.socialmedia.Repositories.FriendshipRepository;
import com.example.socialmedia.Utils.UserUtils;

@Service
public class FriendshipService {

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private UserUtils userUtils;

    // Send a friend request
    public void sendFriendRequest(String receiverUsername) {
        String requesterUsername = userUtils.getAuthenticatedUsername();
        User receiver = userUtils.getUserByUsername(receiverUsername);
        User requester = userUtils.getUserByUsername(requesterUsername);

        if (requester.equals(receiver)) {
            throw new FriendshipException("You cannot send a friend request to yourself");
        }

        boolean friendshipExists = friendshipRepository.existsByRequesterAndReceiver(requester, receiver)
                || friendshipRepository.existsByRequesterAndReceiver(receiver, requester);

        if (friendshipExists) {
            throw new FriendshipException("A friend request or friendship already exists between these users");
        }

        Friendship friendship = new Friendship();
        friendship.setRequester(requester);
        friendship.setReceiver(receiver);
        friendship.setStatus(FriendshipStatus.PENDING);
        friendshipRepository.save(friendship);
    }

    // Accept a friend request
    public void acceptFriendRequest(String requesterUsername) {
        String receiverUsername = userUtils.getAuthenticatedUsername();
        User receiver = userUtils.getUserByUsername(receiverUsername);
        User requester = userUtils.getUserByUsername(requesterUsername);

        Friendship friendship = friendshipRepository.findByRequesterAndReceiver(requester, receiver)
                .orElseThrow(() -> new FriendshipException("No friend request found from this user"));

        if (friendship.getStatus() == FriendshipStatus.ACCEPTED) {
            throw new FriendshipException("This friend request is already accepted");
        }

        friendship.setStatus(FriendshipStatus.ACCEPTED);
        friendshipRepository.save(friendship);
    }

    // Reject a friend request
    public void rejectFriendRequest(String requesterUsername) {
        String receiverUsername = userUtils.getAuthenticatedUsername();
        User receiver = userUtils.getUserByUsername(receiverUsername);
        User requester = userUtils.getUserByUsername(requesterUsername);

        Friendship friendship = friendshipRepository.findByRequesterAndReceiver(requester, receiver)
                .orElseThrow(() -> new FriendshipException("No pending friend request from this user"));

        if (friendship.getStatus() != FriendshipStatus.PENDING) {
            throw new FriendshipException("You can't reject a request that is not pending");
        }

        friendshipRepository.delete(friendship);
    }

    // Unfriend a user
    public void unfriend(String usernameToUnfriend) {
        String requesterUsername = userUtils.getAuthenticatedUsername();
        User userToUnfriend = userUtils.getUserByUsername(usernameToUnfriend);
        User requester = userUtils.getUserByUsername(requesterUsername);

        Friendship friendship = friendshipRepository.findByRequesterAndReceiver(requester, userToUnfriend)
                .orElse(friendshipRepository.findByRequesterAndReceiver(userToUnfriend, requester)
                        .orElseThrow(() -> new FriendshipException("No friendship exists between these users")));

        if (friendship.getStatus() == FriendshipStatus.PENDING) {
            throw new FriendshipException("You cannot unfriend a user while the friendship is still pending");
        }

        friendshipRepository.delete(friendship);
    }

    // Get pending friend requests sent by a user
    public List<FollowFriendshipResponse> getSentFriendRequests() {
        String username = userUtils.getAuthenticatedUsername();
        User user = userUtils.getUserByUsername(username);
        return friendshipRepository.findByRequesterAndStatus(user, FriendshipStatus.PENDING).stream()
                .map(friendship -> mapToFriendsResponse(friendship.getReceiver())).toList();
    }

    // Get pending friend requests received by a user
    public List<FollowFriendshipResponse> getReceivedFriendRequests() {
        String username = userUtils.getAuthenticatedUsername();
        User user = userUtils.getUserByUsername(username);
        return friendshipRepository.findByReceiverAndStatus(user, FriendshipStatus.PENDING).stream()
                .map(friendship -> mapToFriendsResponse(friendship.getRequester())).toList();
    }

    // Get friends list
    public List<FollowFriendshipResponse> getFriends() {
        String username = userUtils.getAuthenticatedUsername();
        User user = userUtils.getUserByUsername(username);
        return friendshipRepository.findByRequesterOrReceiver(user, user).stream()
                .filter(friendship -> friendship.getStatus() == FriendshipStatus.ACCEPTED)
                .map(friendship -> mapToFriendsResponse(
                        friendship.getReceiver().equals(user) ? friendship.getReceiver() : friendship.getRequester()))
                .toList();
    }

    private FollowFriendshipResponse mapToFriendsResponse(User user) {
        return new FollowFriendshipResponse(user.getFirstName(), user.getLastName(), user.getUsername(),
                user.getProfilePictureUrl());
    }
}
