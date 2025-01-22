package com.example.socialmedia.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.socialmedia.Entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostIdOrderByCreatedAtDesc(Long postId);

}
