package com.example.socialmedia.Services.impl;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.example.socialmedia.DTOs.MessageRequest;
import com.example.socialmedia.Entities.Message;
import com.example.socialmedia.Entities.User;
import com.example.socialmedia.Repositories.MessageRepository;
import com.example.socialmedia.Services.IMessageService;
import com.example.socialmedia.Utils.UserUtils;

@Service
public class MessageService implements IMessageService {

    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserUtils userUtils;

    public MessageService(MessageRepository messageRepository, SimpMessagingTemplate messagingTemplate,
            UserUtils userUtils) {
        this.messageRepository = messageRepository;
        this.messagingTemplate = messagingTemplate;
        this.userUtils = userUtils;
    }

    public void sendMessage(MessageRequest messageRequest) {
        User sender = userUtils.getAuthenticatedUser();

        User receiver = userUtils.getUserByUsername(messageRequest.getReceiverUsername());

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(messageRequest.getContent());
        messageRepository.save(message);

        messagingTemplate.convertAndSendToUser(
                receiver.getUsername(), "/queue/messages", messageRequest);
    }

}
