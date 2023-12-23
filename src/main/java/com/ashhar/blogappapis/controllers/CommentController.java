package com.ashhar.blogappapis.controllers;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashhar.blogappapis.payloads.ApiResponse;
import com.ashhar.blogappapis.payloads.CommentDto;
import com.ashhar.blogappapis.services.CommentService;

@RestController
@RequestMapping("api/")
public class CommentController {
	@Autowired
	private CommentService commentService;
	// create comment
	@PostMapping("post/{postId}/user/{userId}/comments")
	public ResponseEntity<CommentDto> createComment(
			@RequestBody CommentDto commentDto,
			@PathVariable("postId") String postId,
			@PathVariable("userId") String userId
			){
		CommentDto createdCommentDto=this.commentService.createComment(commentDto, postId ,userId);
		return new ResponseEntity<>(createdCommentDto,HttpStatus.CREATED);
	}
	
	@DeleteMapping("comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(
			@PathVariable("commentId") String commentId
			){
		this.commentService.deleteComment(commentId);
		String message= MessageFormat.format("Comment associated to id: {0} has been successfully deleted",commentId);
		ApiResponse apiResponse=new ApiResponse(message,true);
		return new ResponseEntity<>(apiResponse,HttpStatus.ACCEPTED);
	}

}
