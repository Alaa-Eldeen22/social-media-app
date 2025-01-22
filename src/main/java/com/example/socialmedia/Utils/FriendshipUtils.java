package com.example.socialmedia.Utils;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.socialmedia.Entities.Friendship;
import com.example.socialmedia.Entities.User;
import com.example.socialmedia.Enums.FriendshipStatus;
import com.example.socialmedia.Exception.FriendshipException;
import com.example.socialmedia.Repositories.FriendshipRepository;

public class FriendshipUtils {

    @Autowired
    FriendshipRepository friendshipRepository;

    @Autowired
    UserUtils userUtils;

    // Helper method to check if a friendship exists
    public boolean doesFriendshipExist(User user1, User user2) {
        return friendshipRepository.existsByRequesterAndReceiver(user1, user2) ||
                friendshipRepository.existsByRequesterAndReceiver(user2, user1);
    }

    public void validateFriendRequest(User receiver, User requester) {
        if (requester.equals(receiver)) {
            throw new FriendshipException("You cannot send a friend request to yourself.");
        }
        if (doesFriendshipExist(requester, receiver)) {
            throw new FriendshipException("Friendship or friend request already exists.");
        }
    }

    public Friendship getPendingFriendship(String requesterUsername) {
        User receiver = userUtils.getAuthenticatedUser();
        User requester = userUtils.getUserByUsername(requesterUsername);
        return friendshipRepository.findByRequesterAndReceiver(requester, receiver)
                .filter(f -> f.getStatus() == FriendshipStatus.PENDING)
                .orElseThrow(() -> new FriendshipException("No pending friend request found."));
    }

    public Friendship findExistingFriendship(String usernameToUnfriend) {
        User requester = userUtils.getAuthenticatedUser();
        User userToUnfriend = userUtils.getUserByUsername(usernameToUnfriend);

        return friendshipRepository.findByRequesterAndReceiver(requester, userToUnfriend)
                .or(() -> friendshipRepository.findByRequesterAndReceiver(userToUnfriend, requester))
                .orElseThrow(() -> new FriendshipException("No friendship exists between these users."));
    }
}
