package com.example.socialmedia.Services;

import java.util.List;

import com.example.socialmedia.DTOs.ReactionRequest;
import com.example.socialmedia.DTOs.ReactionResponse;

public interface IReactionService {

    /**
     * Adds or updates a reaction to a post by the authenticated user.
     *
     * @param postId  the ID of the post to react to.
     * @param request the details of the reaction.
     * @return a response object containing the details of the reaction.
     */
    ReactionResponse reactToPost(Long postId, ReactionRequest request);

    /**
     * Adds or updates a reaction to a comment by the authenticated user.
     *
     * @param commentId the ID of the comment to react to.
     * @param request   the details of the reaction.
     * @return a response object containing the details of the reaction.
     */
    ReactionResponse reactToComment(Long commentId, ReactionRequest request);

    /**
     * Retrieves all reactions associated with a specific post.
     *
     * @param postId the ID of the post.
     * @return a list of responses containing the reactions.
     */
    List<ReactionResponse> getReactionsByPost(Long postId);

    /**
     * Retrieves all reactions associated with a specific comment.
     *
     * @param commentId the ID of the comment.
     * @return a list of responses containing the reactions.
     */
    List<ReactionResponse> getReactionsByComment(Long commentId);

    /**
     * Deletes a reaction by its ID if it belongs to the authenticated user.
     *
     * @param reactionId the ID of the reaction to delete.
     */
    void deleteReact(Long reactionId);
}
