package com.example.socialmedia.Mappers;

import com.example.socialmedia.DTOs.CommentRequest;
import com.example.socialmedia.DTOs.CommentResponse;
import com.example.socialmedia.Entities.Comment;

public class CommentMapper {

    public static CommentResponse toCommentResponse(Comment comment) {
        if (comment == null) {
            return null;
        }
        CommentResponse response = new CommentResponse();
        response.setId(comment.getId());
        response.setContent(comment.getContent());
        response.setUsername(comment.getUser() != null ? comment.getUser().getUsername() : null);
        response.setPostId(comment.getPost() != null ? comment.getPost().getId() : null);
        response.setCreatedAt(comment.getCreatedAt());
        return response;
    }

    public static Comment toComment(CommentRequest request) {
        if (request == null) {
            return null;
        }
        Comment comment = new Comment();
        comment.setContent(request.getContent());
        // Other fields such as `user` and `post` will be set later in the service layer
        return comment;
    }
}
