package com.example.socialmedia.Services;

import com.example.socialmedia.DTOs.ProfileResponse;
import com.example.socialmedia.DTOs.UpdateProfileRequest;
import com.example.socialmedia.Exception.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {

    /**
     * Retrieves the profile information of a user by their username.
     *
     * @param username the username of the user whose profile is retrieved.
     * @return a response object containing the user's profile details.
     * @throws UserNotFoundException if the user is not found.
     */
    ProfileResponse getProfile(String username) throws UserNotFoundException;

    /**
     * Updates the profile information of a user.
     *
     * @param username             the username of the user whose profile is
     *                             updated.
     * @param updateProfileRequest the updated profile details.
     * @return a response object containing the updated profile details.
     * @throws UserNotFoundException if the user is not found.
     */
    ProfileResponse updateProfile(String username, UpdateProfileRequest updateProfileRequest)
            throws UserNotFoundException;
}
