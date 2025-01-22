package com.example.socialmedia.Services;

import java.util.List;

import com.example.socialmedia.DTOs.CreatePostRequest;
import com.example.socialmedia.DTOs.PostResponse;
import com.example.socialmedia.DTOs.UpdatePostRequest;

public interface IPostService {

    /**
     * Creates a new post for the authenticated user.
     *
     * @param request the details of the post to create.
     * @return a response object containing the created post's details.
     */
    PostResponse createPost(CreatePostRequest request);

    /**
     * Retrieves posts created by a specific user, ordered by creation date.
     *
     * @param username the username of the user whose posts are retrieved.
     * @return a list of post responses.
     */
    List<PostResponse> getPostsByUser(String username);

    /**
     * Updates an existing post of the authenticated user.
     *
     * @param postId  the ID of the post to update.
     * @param request the updated details of the post.
     * @return a response object containing the updated post's details.
     */
    PostResponse updatePost(Long postId, UpdatePostRequest request);

    /**
     * Deletes an existing post of the authenticated user.
     *
     * @param postId the ID of the post to delete.
     */
    void deletePost(Long postId);
}
