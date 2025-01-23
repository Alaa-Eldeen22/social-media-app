package com.example.socialmedia.Utils;

import com.example.socialmedia.Entities.User;
import com.example.socialmedia.Exception.UnauthorizedException;
import com.example.socialmedia.Exception.UserNotFoundException;
import com.example.socialmedia.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import org.springframework.security.core.Authentication;

@Component
public class UserUtils {
    @Autowired
    private UserRepository userRepository;

    @Cacheable("users")
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }

    public User getAuthenticatedUser() {
        String username = getAuthenticatedUsername();
        return getUserByUsername(username);

    }

    private String getAuthenticatedUsername() {
        // Get the authentication object from the SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername();
            }
        }

        // Throw a runtime exception if there is no authenticated user
        throw new UnauthorizedException("No authenticated user found");
    }
}