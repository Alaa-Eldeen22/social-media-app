package com.example.socialmedia.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.socialmedia.DTOs.CommentResponse;
import com.example.socialmedia.DTOs.CreateCommentRequest;
import com.example.socialmedia.Entities.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "post.id", target = "postId")
    CommentResponse toCommentResponse(Comment comment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "post", ignore = true)
    Comment toComment(CreateCommentRequest request);
}
