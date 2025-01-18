package com.example.socialmedia.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.socialmedia.DTOs.FollowFriendshipResponse;
import com.example.socialmedia.Services.FriendshipService;

@RestController
@RequestMapping("/api/friendships")
public class FriendshipController {

    @Autowired
    private FriendshipService friendshipService;

    // Send a friend request
    @PostMapping("/send-request")
    public ResponseEntity<String> sendFriendRequest(@RequestParam String receiverUsername) {
        friendshipService.sendFriendRequest(receiverUsername);
        return ResponseEntity.ok("Friend request sent successfully");
    }

    // Accept a friend request
    @PostMapping("/accept-request")
    public ResponseEntity<String> acceptFriendRequest(@RequestParam String requesterUsername) {
        friendshipService.acceptFriendRequest(requesterUsername);
        return ResponseEntity.ok("Friend request accepted successfully");
    }

    // Reject a friend request
    @PostMapping("/reject-request")
    public ResponseEntity<String> rejectFriendRequest(@RequestParam String requesterUsername) {
        friendshipService.rejectFriendRequest(requesterUsername);
        return ResponseEntity.ok("Friend request rejected successfully");
    }

    // Unfriend a user
    @PostMapping("/unfriend")
    public ResponseEntity<String> unfriend(@RequestParam String usernameToUnfriend) {
        friendshipService.unfriend(usernameToUnfriend);
        return ResponseEntity.ok("Unfriended successfully");
    }

    // Get pending friend requests sent by the user
    @GetMapping("/sent-requests")
    public ResponseEntity<List<FollowFriendshipResponse>> getSentFriendRequests() {
        List<FollowFriendshipResponse> sentRequests = friendshipService.getSentFriendRequests();
        return ResponseEntity.ok(sentRequests);
    }

    // Get pending friend requests received by the user
    @GetMapping("/received-requests")
    public ResponseEntity<List<FollowFriendshipResponse>> getReceivedFriendRequests() {
        List<FollowFriendshipResponse> receivedRequests = friendshipService.getReceivedFriendRequests();
        return ResponseEntity.ok(receivedRequests);
    }

    // Get friends list
    @GetMapping("/friends")
    public ResponseEntity<List<FollowFriendshipResponse>> getFriends() {
        List<FollowFriendshipResponse> friends = friendshipService.getFriends();
        return ResponseEntity.ok(friends);
    }
}
