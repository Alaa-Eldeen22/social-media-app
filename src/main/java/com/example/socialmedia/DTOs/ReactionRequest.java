package com.example.socialmedia.DTOs;

import com.example.socialmedia.Enums.ReactionType;

import lombok.Data;

@Data
public class ReactionRequest {
    
   private ReactionType type;
}
