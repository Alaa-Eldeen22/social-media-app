package com.example.socialmedia.Repositories;

import com.example.socialmedia.Entities.Follow;
import com.example.socialmedia.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    // Find all followers of a user
    List<Follow> findByFollowed(User followed);

    // Find all users that a user is following
    List<Follow> findByFollower(User follower);

    // Check if a user is following another user
    boolean existsByFollowerAndFollowed(User follower, User followed);

    // Delete a follow relationship
    void deleteByFollowerAndFollowed(User follower, User followed);
}