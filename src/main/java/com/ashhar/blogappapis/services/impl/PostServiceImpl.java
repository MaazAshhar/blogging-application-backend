package com.ashhar.blogappapis.services.impl;

import com.ashhar.blogappapis.entities.Category;
import com.ashhar.blogappapis.entities.Post;
import com.ashhar.blogappapis.entities.User;
import com.ashhar.blogappapis.exceptions.IllegalPageException;
import com.ashhar.blogappapis.exceptions.IllegalUUIDException;
import com.ashhar.blogappapis.exceptions.ResourceNotFoundException;
import com.ashhar.blogappapis.payloads.PostDto;
import com.ashhar.blogappapis.payloads.PostResponse;
import com.ashhar.blogappapis.repositories.CategoryRepo;
import com.ashhar.blogappapis.repositories.PostRepo;
import com.ashhar.blogappapis.repositories.UserRepo;
import com.ashhar.blogappapis.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    // create
    @Override
    public PostDto createPost(PostDto postDto, String userId, String categoryId) {
    	UUID userIds = null;
        try {
            userIds = UUID.fromString(userId);
        } catch (IllegalArgumentException e) {
            throw new IllegalUUIDException("Given user id is not a valid UUID");
        }
        UUID finalUserIds = userIds;
        User user=this.userRepo.findById(userIds).orElseThrow(()->new ResourceNotFoundException("User","Id", finalUserIds));
        UUID cId = null;
        try {
            cId = UUID.fromString(categoryId);
        } catch (IllegalArgumentException e) {
            throw new IllegalUUIDException("Given category id is not a valid UUID");
        }
    	UUID finalcId=cId;
        Category category=this.categoryRepo.findById(finalcId)
                .orElseThrow(()->new ResourceNotFoundException("Category","categoryId",finalcId));
        Post post=this.modelMapper.map(postDto,Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setCategory(category);
        post.setUser(user);
        Post newPost=this.postRepo.save(post);
        return this.modelMapper.map(newPost,PostDto.class);
    }
    
    //update
    @Override
    public PostDto updatePost(PostDto postDto, String postId) {
    	UUID pId = null;
        try {
            pId = UUID.fromString(postId);
        } catch (IllegalArgumentException e) {
            throw new IllegalUUIDException();
        }
    	UUID finalpId=pId;
        Post post=this.postRepo.findById(finalpId).orElseThrow(()->new ResourceNotFoundException("Post","post_id",finalpId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post updatedPost=this.postRepo.save(post);
        return this.modelMapper.map(updatedPost,PostDto.class);
    }
    
    // get post by id
    @Override
    public PostDto getPostById(String postId) {
    	UUID pId = null;
        try {
            pId = UUID.fromString(postId);
        } catch (IllegalArgumentException e) {
            throw new IllegalUUIDException();
        }
    	UUID finalpId=pId;
        Post post=this.postRepo.findById(finalpId).orElseThrow(()->new ResourceNotFoundException("Post","post_id",finalpId));
        PostDto postDto=this.modelMapper.map(post,PostDto.class);
        return postDto;
    }

    // get all posts
    @Override
    public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,Boolean sortDir) {
    	if(pageNumber<0)throw new IllegalPageException("Page number must not be less than 0");
    	if(pageSize<1)throw new IllegalPageException("Page size must be greater than 0");
    	Sort sort=null;
    	if(sortDir) {
    		sort=Sort.by(sortBy).ascending();
    	}else {
    		sort=Sort.by(sortBy).descending();
    	}
    	Pageable page=PageRequest.of(pageNumber,pageSize,sort);
        Page<Post> pagePosts=this.postRepo.findAll(page);
        List<Post> posts=pagePosts.getContent();
        List<PostDto> postDtos=new ArrayList<>();
        for(Post post:posts){
            PostDto postDto=this.modelMapper.map(post,PostDto.class);
            postDtos.add(postDto);
        }
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePosts.getNumber());
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setTotalPages(pagePosts.getTotalPages());
        postResponse.setLastPage(pagePosts.isLast());
        
        return postResponse;
    }

    // delete post
    @Override
    public void deletePost(String postId) {
    	UUID pId = null;
        try {
            pId = UUID.fromString(postId);
        } catch (IllegalArgumentException e) {
            throw new IllegalUUIDException();
        }
    	UUID finalpId=pId;
        Post post=this.postRepo.findById(finalpId).orElseThrow(()->new ResourceNotFoundException("Post","post_id",finalpId));
        this.postRepo.delete(post);
    }

    // get all posts by category
    @Override
    public PostResponse getAllPostsByCategory(String categoryId,Integer pageNumber,Integer pageSize,String sortBy,Boolean sortDir) {
    	if(pageNumber<0)throw new IllegalPageException("Page number must not be less than 0");
    	if(pageSize<1)throw new IllegalPageException("Page size must be greater than 0");
    	Sort sort=null;
    	if(sortDir) {
    		sort=Sort.by(sortBy).ascending();
    	}else {
    		sort=Sort.by(sortBy).descending();
    	}
    	UUID cId = null;
        try {
            cId = UUID.fromString(categoryId);
        } catch (IllegalArgumentException e) {
            throw new IllegalUUIDException();
        }
    	UUID finalcId=cId;
        Category category=this.categoryRepo.findById(finalcId)
                .orElseThrow(()->new ResourceNotFoundException("Category","categoryId",finalcId));
        Pageable page=PageRequest.of(pageNumber, pageSize,sort);
        Page<Post> pagePosts=this.postRepo.getPostByCategory(category,page);
        List<Post> posts=pagePosts.getContent();
        List<PostDto> postDtos=posts.stream().map((post)->{
            return this.modelMapper.map(post,PostDto.class);
        }).collect(Collectors.toList());
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePosts.getNumber());
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setTotalPages(pagePosts.getTotalPages());
        postResponse.setLastPage(pagePosts.isLast());
        return postResponse;
    }
    
    // get all post by user
    @Override
    public PostResponse getAllPostsByUser(String userId,Integer pageNumber,Integer pageSize,String sortBy,Boolean sortDir) {
    	if(pageNumber<0)throw new IllegalPageException("Page number must not be less than 0");
    	if(pageSize<1)throw new IllegalPageException("Page size must be greater than 0");
    	Sort sort=null;
    	if(sortDir) {
    		sort=Sort.by(sortBy).ascending();
    	}else {
    		sort=Sort.by(sortBy).descending();
    	}
    	UUID userIds = null;
        try {
            userIds = UUID.fromString(userId);
        } catch (IllegalArgumentException e) {
            throw new IllegalUUIDException();
        }
        UUID finalUserIds = userIds;
        User user=this.userRepo.findById(userIds).orElseThrow(()->new ResourceNotFoundException("User","Id", finalUserIds));
        Pageable page=PageRequest.of(pageNumber, pageSize,sort);
        Page<Post> pagePosts=this.postRepo.getPostByUser(user, page);
        List<Post> posts=pagePosts.getContent();
        List<PostDto> postDtos=new ArrayList<>();
        posts.forEach((post)->{
            PostDto postDto=this.modelMapper.map(post,PostDto.class);
            postDtos.add(postDto);
        });
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePosts.getNumber());
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setTotalPages(pagePosts.getTotalPages());
        postResponse.setLastPage(pagePosts.isLast());
        ;
        return postResponse;
    }

    // search post by title
    @Override
    public List<PostDto> searchPosts(String keyword) {
    	List<Post> posts=this.postRepo.findByTitleContaining(keyword);
    	List<PostDto> postDtos=posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    // get all posts associated to user and category
    @Override
    public PostResponse getPostsByUserAndCategory(String userId, String categoryId,Integer pageNumber,Integer pageSize,String sortBy,Boolean sortDir) {
    	if(pageNumber<0)throw new IllegalPageException("Page number must not be less than 0");
    	if(pageSize<1)throw new IllegalPageException("Page size must be greater than 0");
    	Sort sort=null;
    	if(sortDir) {
    		sort=Sort.by(sortBy).ascending();
    	}else {
    		sort=Sort.by(sortBy).descending();
    	}
    	Pageable page=PageRequest.of(pageNumber, pageSize,sort);
    	UUID userIds = null;
        try {
            userIds = UUID.fromString(userId);
        } catch (IllegalArgumentException e) {
            throw new IllegalUUIDException("Given user id is not a valid UUID");
        }
        UUID finalUserIds = userIds;
        User user=this.userRepo.findById(userIds).orElseThrow(()->new ResourceNotFoundException("User","Id", finalUserIds));
        UUID cId = null;
        try {
            cId = UUID.fromString(categoryId);
        } catch (IllegalArgumentException e) {
            throw new IllegalUUIDException("Given category id is not a valid UUID");
        }
    	UUID finalcId=cId;
        Category category=this.categoryRepo.findById(finalcId)
                .orElseThrow(()->new ResourceNotFoundException("Category","categoryId",finalcId));
        Page<Post> pagePosts=this.postRepo.getPostByUserAndCategory(user, category, page);
        List<Post> posts=pagePosts.getContent();
        List<PostDto> postDtos=posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePosts.getNumber());
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setTotalPages(pagePosts.getTotalPages());
        postResponse.setLastPage(pagePosts.isLast());
        
        return postResponse;
    }
}
