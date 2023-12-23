package com.ashhar.blogappapis.repositories;

import com.ashhar.blogappapis.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepo extends JpaRepository<Category, UUID> {
}
