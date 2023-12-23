package com.ashhar.blogappapis.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ashhar.blogappapis.entities.Comment;
import com.ashhar.blogappapis.entities.Post;
import com.ashhar.blogappapis.entities.User;

public interface CommentRepo extends JpaRepository<Comment, UUID> {
	public List<Comment> findByUser(User user);
	public List<Comment> findByPost(Post post);
	public Page<Comment> findByPost(Post post,Pageable page);
	public Page<Comment> findByUser(User user,Pageable page);
}
