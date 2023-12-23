package com.ashhar.blogappapis.services.impl;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashhar.blogappapis.entities.Comment;
import com.ashhar.blogappapis.entities.Post;
import com.ashhar.blogappapis.entities.User;
import com.ashhar.blogappapis.exceptions.IllegalUUIDException;
import com.ashhar.blogappapis.exceptions.ResourceNotFoundException;
import com.ashhar.blogappapis.payloads.CommentDto;
import com.ashhar.blogappapis.repositories.CommentRepo;
import com.ashhar.blogappapis.repositories.PostRepo;
import com.ashhar.blogappapis.repositories.UserRepo;
import com.ashhar.blogappapis.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, String postId, String userId) {

		UUID pId=null;
		try {
			pId=UUID.fromString(postId);
		}catch (IllegalArgumentException e) {
            throw new IllegalUUIDException("Given post_id is not a valid UUID");
        }
		UUID finalPid=pId;
		Post post=this.postRepo.findById(finalPid).orElseThrow(()->new ResourceNotFoundException("Post","Id",finalPid));
		UUID uId=null;
		try {
			uId=UUID.fromString(userId);
		}catch (IllegalArgumentException e) {
            throw new IllegalUUIDException("Given user_id is not a valid UUID");
        }
		UUID finalUid=uId;
		User user=this.userRepo.findById(finalUid).orElseThrow(()->new ResourceNotFoundException("User", "Id", finalUid));
		Comment comment=this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		comment.setUser(user);
		Comment savedComment=this.commentRepo.save(comment);
		CommentDto savedCommentDto=this.modelMapper.map(savedComment, CommentDto.class);
		return savedCommentDto;
	}

	@Override
	public void deleteComment(String commentId) {
		UUID comId=null;
		try {
			comId=UUID.fromString(commentId);
		}catch (IllegalArgumentException e) {
            throw new IllegalUUIDException("Given comment_id is not a valid UUID");
        }
		UUID finalComId=comId;
		Comment comment=this.commentRepo.findById(finalComId).orElseThrow(()->new ResourceNotFoundException("Comment","ID" ,finalComId));
		this.commentRepo.delete(comment);
	}

}
