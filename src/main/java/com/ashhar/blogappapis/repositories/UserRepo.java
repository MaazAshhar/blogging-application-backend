package com.ashhar.blogappapis.repositories;

import com.ashhar.blogappapis.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepo extends JpaRepository<User,UUID> {
}
