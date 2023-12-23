package com.ashhar.blogappapis.services;


import com.ashhar.blogappapis.payloads.PostDto;
import com.ashhar.blogappapis.payloads.PostResponse;

import java.util.List;

public interface PostService {
    // create

    PostDto createPost(PostDto postDto,String userId,String categoryId);

    //update
    PostDto updatePost(PostDto postDto, String postId);

    // get single post
    PostDto getPostById(String postId);

    // get all post
    PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,Boolean sortDir);

    //delete post
    void deletePost(String postId);

    // get all posts by category
    PostResponse getAllPostsByCategory(String categoryId,Integer pageNumber,Integer pageSize,String sortBy,Boolean sortDir);

    // get all Posts by user
    PostResponse getAllPostsByUser(String userId, Integer pageNumber,Integer pageSize,String sortBy,Boolean sortDir);

    // search Post
    List<PostDto> searchPosts(String keyword);
    // get posts by user and category
    PostResponse getPostsByUserAndCategory(String userId,String categoryId,Integer pageNumber,Integer pageSize,String sortBy,Boolean sortDir);
}
