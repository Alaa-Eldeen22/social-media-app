package com.example.userservice.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.userservice.DTOs.ProfileResponse;
import com.example.userservice.DTOs.UpdateProfileRequest;
import com.example.userservice.Services.UserService;

@RestController
@RequestMapping("api/users")
public class ProfileContrller {

    @Autowired
    UserService userService;

    @GetMapping("{username}")
    public ResponseEntity<ProfileResponse> getProfile(@PathVariable String username) {

        ProfileResponse profileResponse = userService.getProfile(username);

        return ResponseEntity.ok(profileResponse);
    }

    @PutMapping("{username}")
    public ResponseEntity<ProfileResponse> updateProfile(@PathVariable String username,
            @RequestBody UpdateProfileRequest request) {

        ProfileResponse profileResponse = userService.updateProfile(username, request);
        return ResponseEntity.ok(profileResponse);
    }
}
