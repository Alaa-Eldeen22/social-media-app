package com.example.socialmedia.Utils;

import com.example.socialmedia.Entities.User;
import com.example.socialmedia.Exception.UserNotFoundException;
import com.example.socialmedia.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserUtils {

    @Autowired
    private UserRepository userRepository;

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }
}