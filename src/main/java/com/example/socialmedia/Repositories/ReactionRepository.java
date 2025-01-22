package com.example.socialmedia.Repositories;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.socialmedia.Entities.Reaction;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    List<Reaction> findByPostId(Long postId);

    List<Reaction> findByCommentId(Long commentId);

    Optional<Reaction> findByUserIdAndPostId(Long userId, Long postId);

    Optional<Reaction> findByUserIdAndCommentId(Long userId, Long commentId);
}
