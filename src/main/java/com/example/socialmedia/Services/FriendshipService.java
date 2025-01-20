package com.example.socialmedia.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.socialmedia.DTOs.FollowFriendshipResponse;
import com.example.socialmedia.Entities.Friendship;
import com.example.socialmedia.Entities.User;
import com.example.socialmedia.Enums.FriendshipStatus;
import com.example.socialmedia.Mappers.FriendshipMapper;
import com.example.socialmedia.Repositories.FriendshipRepository;
import com.example.socialmedia.Utils.FriendshipUtils;
import com.example.socialmedia.Utils.UserUtils;

@Service
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final UserUtils userUtils;
    private final FriendshipUtils friendshipUtils;
    private final FriendshipMapper friendshipMapper;

    public FriendshipService(FriendshipRepository friendshipRepository, UserUtils userUtils,
            FriendshipUtils friendshiptUtils,
            FriendshipMapper friendshipMapper) {
        this.friendshipRepository = friendshipRepository;
        this.userUtils = userUtils;
        this.friendshipUtils = friendshiptUtils;
        this.friendshipMapper = friendshipMapper;
    }

    // Send a friend request
    public void sendFriendRequest(String receiverUsername) {
        User receiver = userUtils.getUserByUsername(receiverUsername);
        User requester = userUtils.getAuthenticatedUser();

        friendshipUtils.validateFriendRequest(receiver, requester);

        Friendship friendship = new Friendship(requester, receiver, FriendshipStatus.PENDING);
        friendshipRepository.save(friendship);
    }

    public void acceptFriendRequest(String requesterUsername) {
        Friendship friendship = friendshipUtils.getPendingFriendship(requesterUsername);
        friendship.setStatus(FriendshipStatus.ACCEPTED);
        friendshipRepository.save(friendship);
    }

    public void rejectFriendRequest(String requesterUsername) {
        Friendship friendship = friendshipUtils.getPendingFriendship(requesterUsername);
        friendshipRepository.delete(friendship);
    }

    // Unfriend a user
    public void unfriend(String usernameToUnfriend) {
        Friendship friendship = friendshipUtils.findExistingFriendship(usernameToUnfriend);
        friendshipRepository.delete(friendship);
    }

    // Get pending friend requests sent by a user
    public List<FollowFriendshipResponse> getSentFriendRequests() {
        User user = userUtils.getAuthenticatedUser();
        return friendshipRepository.findByRequesterAndStatus(user, FriendshipStatus.PENDING).stream()
                .map(friendship -> friendshipMapper.toFollowFriendshipResponse(friendship.getReceiver())).toList();
    }

    // Get pending friend requests received by a user
    public List<FollowFriendshipResponse> getReceivedFriendRequests() {
        User user = userUtils.getAuthenticatedUser();
        return friendshipRepository.findByReceiverAndStatus(user, FriendshipStatus.PENDING).stream()
                .map(friendship -> friendshipMapper.toFollowFriendshipResponse(friendship.getRequester())).toList();
    }

    // Get friends list
    public List<FollowFriendshipResponse> getFriends() {
        User user = userUtils.getAuthenticatedUser();
        return friendshipRepository.findByRequesterOrReceiver(user, user).stream()
                .filter(friendship -> friendship.getStatus() == FriendshipStatus.ACCEPTED)
                .map(friendship -> friendshipMapper.toFollowFriendshipResponse(
                        friendship.getReceiver().equals(user) ? friendship.getReceiver() : friendship.getRequester()))
                .toList();
    }

}
