package com.example.userservice.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.userservice.DTOs.ProfileResponse;
import com.example.userservice.Entities.User;
import com.example.userservice.Repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("user not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.getPassword())
                .authorities("USER")
                .build();
    }

    public ProfileResponse getProfile(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("user not found"));

        ProfileResponse profileResponse = new ProfileResponse();

        profileResponse.setFisrtName(user.getFirstName());
        profileResponse.setLastName(user.getLastName());

        profileResponse.setProfilePictureUrl(user.getProfilePictureUrl());
        profileResponse.setCoverPictureUrl(user.getCoverPictureUrl());

        profileResponse.setBio(user.getBio());

        return profileResponse;
    }
}
