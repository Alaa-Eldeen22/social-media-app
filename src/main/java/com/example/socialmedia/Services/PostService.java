package com.example.socialmedia.Services;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.socialmedia.DTOs.CreatePostRequest;
import com.example.socialmedia.DTOs.PostResponse;
import com.example.socialmedia.DTOs.UpdatePostRequest;
import com.example.socialmedia.Entities.Post;
import com.example.socialmedia.Exception.PostNotFoundException;
import com.example.socialmedia.Mappers.PostMapper;
import com.example.socialmedia.Repositories.PostRepository;
import com.example.socialmedia.Utils.UserUtils;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserUtils userUtils;

    @Autowired
    private PostMapper postMapper;

    public PostResponse createPost(CreatePostRequest request) {

        Post post = postMapper.toPost(request);
        post.setUser(userUtils.getAuthenticatedUser());

        Post savedPost = postRepository.save(post);

        return postMapper.toPostResponse(savedPost);
    }

    public List<PostResponse> getPostsByUser(String username) {

        List<Post> posts = postRepository.findByUserUsernameOrderByCreatedAtDesc(username);
        return posts.stream()
                .map(postMapper::toPostResponse)
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
        return postMapper.toPostResponse(updatedPost);
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
