package com.example.socialmedia.Controllers;

import com.example.socialmedia.DTOs.MessageRequest;
import com.example.socialmedia.Services.impl.MessageService;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PreAuthorize("isAuthenticated()")
    @MessageMapping("/send")
    public void sendMessage(MessageRequest messageRequest) {
        messageService.sendMessage(messageRequest);
    }
}
