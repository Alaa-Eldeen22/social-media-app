package com.example.socialmedia.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.example.socialmedia.DTOs.ProfileResponse;
import com.example.socialmedia.DTOs.UpdateProfileRequest;
import com.example.socialmedia.Entities.User;
import com.example.socialmedia.Exception.UserNotFoundException;
import com.example.socialmedia.Repositories.UserRepository;

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

    public ProfileResponse updateProfile(String username, UpdateProfileRequest updateProfileRequest) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("user not found"));

        if (updateProfileRequest.getFirstName() != null) {
            user.setFirstName(updateProfileRequest.getFirstName());
        }
        if (updateProfileRequest.getLastName() != null) {
            user.setLastName(updateProfileRequest.getLastName());
        }
        if (updateProfileRequest.getBio() != null) {
            user.setBio(updateProfileRequest.getBio());
        }
        if (updateProfileRequest.getProfilePictureUrl() != null) {
            user.setProfilePictureUrl(updateProfileRequest.getProfilePictureUrl());
        }
        if (updateProfileRequest.getCoverPictureUrl() != null) {
            user.setCoverPictureUrl(updateProfileRequest.getCoverPictureUrl());
        }

        userRepository.save(user);

        ProfileResponse profileResponse = new ProfileResponse();
        profileResponse.setFirstName(user.getFirstName());
        profileResponse.setLastName(user.getLastName());
        profileResponse.setBio(user.getBio());
        profileResponse.setProfilePictureUrl(user.getProfilePictureUrl());
        profileResponse.setCoverPictureUrl(user.getCoverPictureUrl());

        return profileResponse;
    }
}
