package com.example.socialmedia.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.socialmedia.DTOs.FollowFriendshipResponse;
import com.example.socialmedia.Services.FriendshipService;

@RestController
@RequestMapping("/api/friendships")
public class FriendshipController {

    @Autowired
    private FriendshipService friendshipService;

    // Send a friend request
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/send-request/{receiverUsername}")
    public ResponseEntity<String> sendFriendRequest(@PathVariable String receiverUsername) {
        friendshipService.sendFriendRequest(receiverUsername);
        return ResponseEntity.ok("Friend request sent successfully");
    }

    // Accept a friend request
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/accept-request/{requesterUsername}")
    public ResponseEntity<String> acceptFriendRequest(@PathVariable String requesterUsername) {
        friendshipService.acceptFriendRequest(requesterUsername);
        return ResponseEntity.ok("Friend request accepted successfully");
    }

    // Reject a friend request
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/reject-request/{requesterUsername}")
    public ResponseEntity<String> rejectFriendRequest(@PathVariable String requesterUsername) {
        friendshipService.rejectFriendRequest(requesterUsername);
        return ResponseEntity.ok("Friend request rejected successfully");
    }

    // Unfriend a user
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/unfriend/{usernameToUnfriend}")
    public ResponseEntity<String> unfriend(@PathVariable String usernameToUnfriend) {
        friendshipService.unfriend(usernameToUnfriend);
        return ResponseEntity.ok("Unfriended successfully");
    }

    // Get pending friend requests sent by the user
    @GetMapping("/sent-requests")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<FollowFriendshipResponse>> getSentFriendRequests() {
        List<FollowFriendshipResponse> sentRequests = friendshipService.getSentFriendRequests();
        return ResponseEntity.ok(sentRequests);
    }

    // Get pending friend requests received by the user
    @GetMapping("/received-requests")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<FollowFriendshipResponse>> getReceivedFriendRequests() {
        List<FollowFriendshipResponse> receivedRequests = friendshipService.getReceivedFriendRequests();
        return ResponseEntity.ok(receivedRequests);
    }

    // Get friends list
    @GetMapping("/friends")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<FollowFriendshipResponse>> getFriends() {
        List<FollowFriendshipResponse> friends = friendshipService.getFriends();
        return ResponseEntity.ok(friends);
    }
}
