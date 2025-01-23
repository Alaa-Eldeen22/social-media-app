package com.example.socialmedia.Mappers;

import com.example.socialmedia.DTOs.ReactionResponse;
import com.example.socialmedia.Entities.Reaction;

public class ReactionMapper {

    public static ReactionResponse toReactionResponse(Reaction reaction) {
        if (reaction == null) {
            return null;
        }
        ReactionResponse response = new ReactionResponse();
        response.setId(reaction.getId());
        response.setType(reaction.getType()); // ReactionType is assumed to be an enum.
        response.setUsername(reaction.getUser() != null ? reaction.getUser().getUsername() : null);
        return response;
    }
}
