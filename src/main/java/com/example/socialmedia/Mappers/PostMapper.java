package com.example.socialmedia.Mappers;

import com.example.socialmedia.DTOs.CreatePostRequest;
import com.example.socialmedia.DTOs.PostResponse;
import com.example.socialmedia.Entities.Post;

public class PostMapper {

    public static PostResponse toPostResponse(Post post) {
        if (post == null) {
            return null;
        }
        PostResponse response = new PostResponse();
        response.setId(post.getId());
        response.setContent(post.getContent());
        response.setCreatedAt(post.getCreatedAt());
        response.setUsername(post.getUser() != null ? post.getUser().getUsername() : null);
        return response;
    }

    public static Post toPost(CreatePostRequest request) {
        if (request == null) {
            return null;
        }
        Post post = new Post();
        post.setContent(request.getContent());
        return post;
    }
}
