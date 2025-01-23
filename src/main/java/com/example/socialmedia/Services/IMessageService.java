package com.example.socialmedia.Services;

import com.example.socialmedia.DTOs.MessageRequest;

public interface IMessageService {

    /**
     * Sends a message from the authenticated user to another user.
     *
     * @param messageRequest the details of the message, including content and the
     *                       receiver's username.
     */
    void sendMessage(MessageRequest messageRequest);
}
