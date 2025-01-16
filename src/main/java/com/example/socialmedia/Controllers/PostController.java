package com.example.socialmedia.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.socialmedia.DTOs.CreatePostRequest;
import com.example.socialmedia.DTOs.PostResponse;
import com.example.socialmedia.DTOs.UpdatePostRequest;
import com.example.socialmedia.Services.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/user/{username}")
    public ResponseEntity<PostResponse> createPost(
            @PathVariable String username,
            @RequestBody CreatePostRequest request) {
        PostResponse postResponse = postService.createPost(username, request);
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<PostResponse>> getPostsByUsers(@PathVariable String username) {
        List<PostResponse> posts = postService.getPostsByUser(username);
        if (posts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(posts);
    }

    @PutMapping("/{postId}/{username}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable Long postId,
            @PathVariable String username,
            @RequestBody UpdatePostRequest request) {
        PostResponse updatedPost = postService.updatePost(postId, username, request);
        if (updatedPost == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedPost);
    }
}