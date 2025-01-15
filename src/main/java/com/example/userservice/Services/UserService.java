package com.example.userservice.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.example.userservice.DTOs.ProfileResponse;
import com.example.userservice.Entities.User;
import com.example.userservice.Exception.UserNotFoundException;
import com.example.userservice.Repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("user not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.getPassword())
                .authorities("USER")
                .build();
    }

    public ProfileResponse getProfile(String username) throws UserNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("user not found"));

        ProfileResponse profileResponse = new ProfileResponse();

        profileResponse.setFirstName(user.getFirstName());
        profileResponse.setLastName(user.getLastName());

        profileResponse.setProfilePictureUrl(user.getProfilePictureUrl());
        profileResponse.setCoverPictureUrl(user.getCoverPictureUrl());

        profileResponse.setBio(user.getBio());

        return profileResponse;
    }
}
