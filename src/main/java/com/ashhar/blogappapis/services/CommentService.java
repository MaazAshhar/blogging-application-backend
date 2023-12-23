package com.ashhar.blogappapis.services;

import com.ashhar.blogappapis.payloads.CommentDto;

public interface CommentService {
	// create comment
	CommentDto createComment(CommentDto commentDto,String postId,String userId);
	
	// delete comment
	
	void deleteComment(String commentId);
}
