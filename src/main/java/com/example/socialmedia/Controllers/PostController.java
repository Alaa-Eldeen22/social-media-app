package com.example.socialmedia.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.webauthn.api.CredProtectAuthenticationExtensionsClientInput.CredProtect;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.socialmedia.DTOs.CreatePostRequest;
import com.example.socialmedia.DTOs.PostResponse;
import com.example.socialmedia.DTOs.UpdatePostRequest;
import com.example.socialmedia.Services.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> creatPost(
            @PathVariable String username,
            @RequestBody CreatePostRequest request) {

        PostResponse postResponse = postService.createPost(username, request);
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<PostResponse>> getPostsByUsers(@PathVariable String username) {
        List<PostResponse> posts = postService.getPostsByUser(username);

        return ResponseEntity.ok(posts);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable Long postId,
            @RequestParam String username,
            @RequestBody UpdatePostRequest request) {

        PostResponse updatedPost = postService.updatePost(postId, username, request);
        
        return ResponseEntity.ok(updatedPost);
    }
}
