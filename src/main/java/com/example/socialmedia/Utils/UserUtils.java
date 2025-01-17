package com.example.socialmedia.Utils;

import com.example.socialmedia.Entities.User;
import com.example.socialmedia.Exception.UserNotFoundException;
import com.example.socialmedia.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserUtils {

    @Autowired
    private UserRepository userRepository;

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }

    public String getAuthenticatedUsername() {
        // Get the authentication object from the SecurityContext
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Cast the principal to UserDetails and return the username
        return ((UserDetails) principal).getUsername();
    }
}