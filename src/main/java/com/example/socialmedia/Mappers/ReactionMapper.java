package com.example.socialmedia.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.socialmedia.DTOs.ReactionResponse;
import com.example.socialmedia.Entities.Reaction;

@Mapper(componentModel = "spring")
public interface ReactionMapper {

    @Mapping(source = "user.username", target = "username")
    ReactionResponse toReactionResponse(Reaction reaction);
}
