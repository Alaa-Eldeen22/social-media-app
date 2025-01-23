package com.example.socialmedia.Services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.socialmedia.DTOs.CommentResponse;
import com.example.socialmedia.DTOs.CommentRequest;
import com.example.socialmedia.Entities.Comment;
import com.example.socialmedia.Entities.Post;
import com.example.socialmedia.Exception.CommentNotFoundException;
import com.example.socialmedia.Exception.PostNotFoundException;
import com.example.socialmedia.Exception.UnauthorizedException;
import com.example.socialmedia.Mappers.CommentMapper;
import com.example.socialmedia.Repositories.CommentRepository;
import com.example.socialmedia.Repositories.PostRepository;
import com.example.socialmedia.Services.ICommentService;
import com.example.socialmedia.Utils.UserUtils;

@Service
public class CommentService implements ICommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private final UserUtils userUtils;

    public CommentService(CommentRepository commentRepository,
            PostRepository postRepository,
            UserUtils userUtils) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userUtils = userUtils;
    }

    public CommentResponse createComment(Long postId, CommentRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + postId));

        Comment comment = CommentMapper.toComment(request);

        comment.setUser(userUtils.getAuthenticatedUser());
        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);

        return CommentMapper.toCommentResponse(savedComment);
    }

    public List<CommentResponse> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostIdOrderByCreatedAtDesc(postId).stream()
                .map(CommentMapper::toCommentResponse).collect(Collectors.toList());
    }

    public CommentResponse updateComment(Long commentId, CommentRequest request) {
        Comment comment = getCommentById(commentId);

        if (request.getContent() != null) {
            comment.setContent(request.getContent());
        }

        Comment updatedComment = commentRepository.save(comment);

        return CommentMapper.toCommentResponse(updatedComment);
    }

    public void deleteComment(Long commentId) {
        Comment comment = getCommentById(commentId);
        commentRepository.delete(comment);
    }

    private Comment getCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found with id: " + commentId));

        if (comment.getUser().getId() != userUtils.getAuthenticatedUser().getId()) {
            throw new UnauthorizedException("You can't update or delete this comment");
        }

        return comment;
    }
}
