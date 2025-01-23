package com.example.socialmedia.DTOs;

import lombok.Data;

@Data
public class MessageRequest {
    private String content;
    private String receiverUsername;

}
