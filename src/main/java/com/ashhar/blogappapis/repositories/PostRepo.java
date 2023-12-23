package com.ashhar.blogappapis.repositories;

import com.ashhar.blogappapis.entities.Category;
import com.ashhar.blogappapis.entities.Post;
import com.ashhar.blogappapis.entities.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostRepo extends JpaRepository<Post, UUID> {
    public Optional<List<Post>> getPostByUser(User user);
    public Optional<List<Post>> getPostByCategory(Category category);
    public Page<Post> getPostByCategory(Category category,Pageable page);
    public Page<Post> getPostByUser(User user,Pageable page);
    public Optional<List<Post>> getPostByUserAndCategory(User user,Category category);
    public Page<Post> getPostByUserAndCategory(User user,Category category,Pageable page);
    public List<Post> findByTitleContaining(String title);
}
