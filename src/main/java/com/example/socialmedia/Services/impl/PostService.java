package com.example.socialmedia.Services.impl;

import java.util.stream.Collectors;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.socialmedia.DTOs.CreatePostRequest;
import com.example.socialmedia.DTOs.PostResponse;
import com.example.socialmedia.DTOs.UpdatePostRequest;
import com.example.socialmedia.Entities.Post;
import com.example.socialmedia.Exception.PostNotFoundException;
import com.example.socialmedia.Mappers.PostMapper;
import com.example.socialmedia.Repositories.PostRepository;
import com.example.socialmedia.Services.IPostService;
import com.example.socialmedia.Utils.UserUtils;

@Service
public class PostService implements IPostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserUtils userUtils;

    public PostResponse createPost(CreatePostRequest request) {
        Post post = PostMapper.toPost(request); // Use manual mapper
        post.setUser(userUtils.getAuthenticatedUser());

        Post savedPost = postRepository.save(post);

        return PostMapper.toPostResponse(savedPost); // Use manual mapper
    }

    public List<PostResponse> getPostsByUser(String username) {
        List<Post> posts = postRepository.findByUserUsernameOrderByCreatedAtDesc(username);

        return posts.stream()
                .map(PostMapper::toPostResponse) // Use manual mapper
                .collect(Collectors.toList());
    }

    public PostResponse updatePost(Long postId, UpdatePostRequest request) {
        String username = userUtils.getAuthenticatedUser().getUsername();

        Post post = findPostByIdAndUsername(postId, username);

        if (request.getContent() != null) {
            post.setContent(request.getContent());
        }
        if (request.getImageUrl() != null) {
            post.setImageUrl(request.getImageUrl());
        }

        Post updatedPost = postRepository.save(post);
        return PostMapper.toPostResponse(updatedPost); // Use manual mapper
    }

    public void deletePost(Long postId) {
        String username = userUtils.getAuthenticatedUser().getUsername();

        Post post = findPostByIdAndUsername(postId, username);

        postRepository.delete(post);
    }

    private Post findPostByIdAndUsername(Long postId, String username) {
        return postRepository.findByIdAndUserUsername(postId, username)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + postId));
    }
}
