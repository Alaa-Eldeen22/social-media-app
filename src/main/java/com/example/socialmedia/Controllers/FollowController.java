package com.example.socialmedia.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.socialmedia.DTOs.FollowerResponse;
import com.example.socialmedia.Services.FollowService;

@RestController
@RequestMapping("/api/follows")
public class FollowController {

    @Autowired
    private FollowService followService;

    @PostMapping("/follow")
    public ResponseEntity<String> followUser(
            @RequestParam String followerUsername,
            @RequestParam String followedUsername) {
        followService.followUser(followerUsername, followedUsername);
        return ResponseEntity.ok("Followed successfully");
    }

    @PostMapping("/unfollow")
    public ResponseEntity<String> unfollowUser(
            @RequestParam String followerUsername,
            @RequestParam String followedUsername) {
        followService.unfollowUser(followerUsername, followedUsername);
        return ResponseEntity.ok("Unfollowed successfully");
    }

    @GetMapping("/followers/{username}")
    public ResponseEntity<List<FollowerResponse>> getFollowers(@PathVariable String username) {
        List<FollowerResponse> followers = followService.getFollowers(username);
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/following/{username}")
    public ResponseEntity<List<FollowerResponse>> getFollowing(@PathVariable String username) {
        List<FollowerResponse> following = followService.getFollowing(username);
        return ResponseEntity.ok(following);
    }
}