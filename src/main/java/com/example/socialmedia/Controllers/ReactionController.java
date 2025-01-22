package com.example.socialmedia.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.socialmedia.DTOs.ReactionRequest;
import com.example.socialmedia.DTOs.ReactionResponse;
import com.example.socialmedia.Services.impl.ReactionService;

@RestController
@RequestMapping("/api/reactions")
public class ReactionController {

    private final ReactionService reactionService;

    public ReactionController(ReactionService reactionService) {
        this.reactionService = reactionService;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/post/{postId}")
    public ResponseEntity<ReactionResponse> reactToPost(
            @PathVariable Long postId, @RequestBody ReactionRequest request) {
        ReactionResponse response = reactionService.reactToPost(postId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/comment/{commentId}")
    public ResponseEntity<ReactionResponse> reactToComment(
            @PathVariable Long commentId, @RequestBody ReactionRequest request) {
        ReactionResponse response = reactionService.reactToComment(commentId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<ReactionResponse>> getReactionsByPost(@PathVariable Long postId) {
        List<ReactionResponse> responses = reactionService.getReactionsByPost(postId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/comment/{commentId}")
    public ResponseEntity<List<ReactionResponse>> getReactionsByComment(@PathVariable Long commentId) {
        List<ReactionResponse> responses = reactionService.getReactionsByComment(commentId);
        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{reactionId}")
    public ResponseEntity<Void> deleteReaction(@PathVariable Long reactionId) {
        reactionService.deleteReact(reactionId);
        return ResponseEntity.noContent().build();
    }
}
