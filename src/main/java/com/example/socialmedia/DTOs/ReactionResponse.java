package com.example.socialmedia.DTOs;

import com.example.socialmedia.Enums.ReactionType;

import lombok.Data;

@Data
public class ReactionResponse {

    private Long id;
    private String username;
    private ReactionType type;

}
