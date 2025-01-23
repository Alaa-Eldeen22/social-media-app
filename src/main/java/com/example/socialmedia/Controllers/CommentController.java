package com.example.socialmedia.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.socialmedia.DTOs.CommentResponse;
import com.example.socialmedia.Services.impl.CommentService;
import com.example.socialmedia.DTOs.CommentRequest;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{postId}")
    public ResponseEntity<CommentResponse> createComment(
            @RequestBody CommentRequest request,
            @PathVariable Long postId) {

        CommentResponse response = commentService.createComment(postId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentResponse>> getCommentsByPostId(@PathVariable Long postId) {
        List<CommentResponse> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @RequestBody CommentRequest request,
            @PathVariable Long commentId) {

        CommentResponse response = commentService.updateComment(commentId, request);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable Long commentId) {

        commentService.deleteComment(commentId);
        return ResponseEntity.ok("Comment deleted successfullu");
    }
}
