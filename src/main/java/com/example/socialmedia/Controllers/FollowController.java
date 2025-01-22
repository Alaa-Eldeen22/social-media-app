package com.example.socialmedia.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.socialmedia.DTOs.FollowFriendshipResponse;
import com.example.socialmedia.Services.impl.FollowService;

@RestController
@RequestMapping("/api/follows")
public class FollowController {

    @Autowired
    private FollowService followService;

    @PreAuthorize("isAuthenczticated()")
    @PostMapping("/follow")    
    public ResponseEntity<String> followUser(
            @RequestParam String followedUsername) {
        followService.followUser(followedUsername);
        return ResponseEntity.ok("Followed successfully");
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/unfollow")
    public ResponseEntity<String> unfollowUser(
            @RequestParam String followedUsername) {
        followService.unfollowUser(followedUsername);
        return ResponseEntity.ok("Unfollowed successfully");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/followers")
    public ResponseEntity<List<FollowFriendshipResponse>> getFollowers() {
        List<FollowFriendshipResponse> followers = followService.getFollowers();
        return ResponseEntity.ok(followers);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/following")
    public ResponseEntity<List<FollowFriendshipResponse>> getFollowing() {
        List<FollowFriendshipResponse> following = followService.getFollowing();
        return ResponseEntity.ok(following);
    }
}