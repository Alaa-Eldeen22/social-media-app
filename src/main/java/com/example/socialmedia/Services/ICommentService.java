package com.example.socialmedia.Services;

import java.util.List;

import com.example.socialmedia.DTOs.CommentResponse;
import com.example.socialmedia.DTOs.CommentRequest;

public interface ICommentService {

    /**
     * Creates a new comment for the specified post.
     *
     * @param postId  the ID of the post to associate the comment with.
     * @param request the details of the comment to create.
     * @return a response object containing the created comment's details.
     */
    CommentResponse createComment(Long postId, CommentRequest request);

    /**
     * Retrieves all comments for a specific post, ordered by creation date.
     *
     * @param postId the ID of the post for which comments are retrieved.
     * @return a list of comment responses.
     */
    List<CommentResponse> getCommentsByPostId(Long postId);

    /**
     * Updates the content of an existing comment.
     *
     * @param commentId the ID of the comment to update.
     * @param request   the updated details of the comment.
     * @return a response object containing the updated comment's details.
     */
    CommentResponse updateComment(Long commentId, CommentRequest request);

    /**
     * Deletes an existing comment by its ID.
     *
     * @param commentId the ID of the comment to delete.
     */
    void deleteComment(Long commentId);
}
