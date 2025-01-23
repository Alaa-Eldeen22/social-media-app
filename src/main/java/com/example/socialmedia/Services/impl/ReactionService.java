package com.example.socialmedia.Services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.socialmedia.DTOs.ReactionRequest;
import com.example.socialmedia.DTOs.ReactionResponse;
import com.example.socialmedia.Entities.Comment;
import com.example.socialmedia.Entities.Post;
import com.example.socialmedia.Entities.Reaction;
import com.example.socialmedia.Exception.CommentNotFoundException;
import com.example.socialmedia.Exception.PostNotFoundException;
import com.example.socialmedia.Exception.UnauthorizedException;
import com.example.socialmedia.Mappers.ReactionMapper;
import com.example.socialmedia.Repositories.CommentRepository;
import com.example.socialmedia.Repositories.PostRepository;
import com.example.socialmedia.Repositories.ReactionRepository;
import com.example.socialmedia.Services.IReactionService;
import com.example.socialmedia.Utils.UserUtils;

@Service
public class ReactionService implements IReactionService {

    private final ReactionRepository reactionRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserUtils userUtils;

    public ReactionService(ReactionRepository reactionRepository, PostRepository postRepository,
            CommentRepository commentRepository,
            UserUtils userUtils) {
        this.reactionRepository = reactionRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userUtils = userUtils;
    }

    public ReactionResponse reactToPost(Long postId, ReactionRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + postId));

        Long userId = userUtils.getAuthenticatedUser().getId();

        Reaction reaction = reactionRepository.findByUserIdAndPostId(userId, postId)
                .orElseGet(() -> new Reaction());

        reaction.setType(request.getType());
        reaction.setUser(userUtils.getAuthenticatedUser());
        reaction.setPost(post);
        Reaction savedReaction = reactionRepository.save(reaction);

        return ReactionMapper.toReactionResponse(savedReaction); // Use manual mapper
    }

    public ReactionResponse reactToComment(Long commentId, ReactionRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found with id: " + commentId));

        Long userId = userUtils.getAuthenticatedUser().getId();
        Reaction reaction = reactionRepository.findByUserIdAndCommentId(userId, commentId)
                .orElseGet(() -> new Reaction());

        reaction.setType(request.getType());
        reaction.setUser(userUtils.getAuthenticatedUser());
        reaction.setComment(comment);

        Reaction savedReaction = reactionRepository.save(reaction);
        return ReactionMapper.toReactionResponse(savedReaction); // Use manual mapper
    }

    public List<ReactionResponse> getReactionsByPost(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new PostNotFoundException("Post not found with id: " + postId);
        }

        return reactionRepository.findByPostId(postId).stream()
                .map(ReactionMapper::toReactionResponse) // Use manual mapper
                .toList();
    }

    public List<ReactionResponse> getReactionsByComment(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new CommentNotFoundException("Comment not found with id: " + commentId);
        }

        return reactionRepository.findByCommentId(commentId).stream()
                .map(ReactionMapper::toReactionResponse) // Use manual mapper
                .toList();
    }

    public void deleteReact(Long reactionId) {
        if (reactionRepository.existsById(reactionId)) {
            Reaction reaction = reactionRepository.findById(reactionId).get();
            if (!reaction.getUser().getId().equals(userUtils.getAuthenticatedUser().getId())) {
                throw new UnauthorizedException("You are not allowed to delete this reaction");
            }

            reactionRepository.delete(reaction);
        }
    }
}
