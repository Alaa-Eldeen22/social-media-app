package com.example.socialmedia.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.socialmedia.Entities.Post;
import java.util.Optional;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserUsernameOrderByCreatedAtDesc(String username);

    Optional<Post> findByIdAndUserUsername(Long id, String username);

}