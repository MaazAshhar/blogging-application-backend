package com.ashhar.blogappapis.controllers;

import com.ashhar.blogappapis.payloads.ApiResponse;
import com.ashhar.blogappapis.payloads.ImageUploadedResponse;
import com.ashhar.blogappapis.payloads.PostDto;
import com.ashhar.blogappapis.payloads.PostResponse;
import com.ashhar.blogappapis.services.FileService;
import com.ashhar.blogappapis.services.PostService;
import com.ashhar.blogappapis.utils.AppConstants;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping("api")
public class PostController {
    @Autowired
    private PostService postService;
    
    @Autowired
    private FileService fileService;
    
    @Value("${project.image}")
    private String path;
    
    
    // create post
    @PostMapping("user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto
            ,@PathVariable("categoryId") String cId,
            @PathVariable("userId") String uId){
        PostDto newPost=this.postService.createPost(postDto,uId,cId);
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }

    // get post by user
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<PostResponse> getPostsByUser(@PathVariable("userId") String uid,
    		@RequestParam(value="pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
    		@RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required=false)Integer pageSize,
    		@RequestParam(value="sortBy",defaultValue=AppConstants.SORT_BY,required=false)String sortBy,
    		@RequestParam(value="sortDir",defaultValue=AppConstants.SORT_DIR,required=false)Boolean sortDir){
        PostResponse postResponse=this.postService.getAllPostsByUser(uid,pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

    //get posts by category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getPostsByCategory(@PathVariable("categoryId") String cid,
    		@RequestParam(value="pageNumber", defaultValue=AppConstants.PAGE_NUMBER,required=false)Integer pageNumber,
    		@RequestParam(value="pageSize",defaultValue=AppConstants.PAGE_SIZE,required=false)Integer pageSize,
    		@RequestParam(value="sortBy",defaultValue=AppConstants.SORT_BY,required=false)String sortBy,
    		@RequestParam(value="sortDir",defaultValue=AppConstants.SORT_DIR,required=false)Boolean sortDir){
        PostResponse postResponse=this.postService.getAllPostsByCategory(cid,pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

    // get post by id
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("postId") String pid){
        PostDto postDto=this.postService.getPostById(pid);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }

    // get all Posts
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required=false)Integer pageNumber,
    		@RequestParam(value="pageSize",defaultValue=AppConstants.PAGE_SIZE,required=false)Integer pageSize,
    		@RequestParam(value="sortBy",defaultValue=AppConstants.SORT_BY,required=false)String sortBy,
    		@RequestParam(value="sortDir",defaultValue=AppConstants.SORT_DIR,required=false)Boolean sortDir){
        PostResponse postResponse=this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }
    //get posts associated to user and category
    @GetMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getPostByUserAndCategory(
            @PathVariable("userId") String uid, @PathVariable("categoryId") String cid,
            @RequestParam(value="pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required =false)Integer pageNumber,
            @RequestParam(value ="pageSize",defaultValue = AppConstants.PAGE_SIZE,required=false)Integer pageSize,
            @RequestParam(value="sortBy",defaultValue=AppConstants.SORT_BY,required=false)String sortBy,
    		@RequestParam(value="sortDir",defaultValue=AppConstants.SORT_DIR,required=false)Boolean sortDir
    ){
        PostResponse postResponse=this.postService.getPostsByUserAndCategory(uid,cid,pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

    //delete post
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId") String pid){
        this.postService.deletePost(pid);
        String message= MessageFormat.format("Post asociated to post_id: {0} is successfully deleted",pid);
        ApiResponse apiResponse=new ApiResponse(message,true);
        return new ResponseEntity<>(apiResponse,HttpStatus.ACCEPTED);
    }
    
    //update post
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable("postId") String pid){
    	PostDto updatedPostDto=this.postService.updatePost(postDto, pid);
    	return new ResponseEntity<PostDto>(updatedPostDto,HttpStatus.ACCEPTED);
    }
    
    // search post
    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(
    		@PathVariable("keywords") String keywords
    		){
    	List<PostDto> result=this.postService.searchPosts(keywords);
    	return new ResponseEntity<>(result,HttpStatus.OK);
    	
    }
    
    
    // upload image to post 
    @PostMapping("/posts/image/upload/{postId}")
    public ResponseEntity<ImageUploadedResponse> uploadPostImage(
    		@RequestParam("image") MultipartFile image,
    		@PathVariable("postId") String postId
    		) throws IOException{
    	PostDto postDto=this.postService.getPostById(postId);
    	String fileName=this.fileService.uploadImage(path, image);
    	postDto.setImageName(fileName);
    	PostDto updatedPost= this.postService.updatePost(postDto, postId);
    	ImageUploadedResponse imageResponse=new ImageUploadedResponse();
    	imageResponse.setMessage("Image successfully uploaded");
    	imageResponse.setSuccess(true);
    	imageResponse.setPostDto(updatedPost);
    	return new ResponseEntity<>(imageResponse,HttpStatus.ACCEPTED);
    }
    
    // method to serve image files
    @GetMapping(value="/posts/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
    		@PathVariable("imageName") String imageName,
    		HttpServletResponse response
    		) throws IOException {
    	InputStream resource=this.fileService.getResource(path, imageName);
    	response.setContentType(MediaType.IMAGE_JPEG_VALUE);
    	StreamUtils.copy(resource, response.getOutputStream());
    }
    
}
