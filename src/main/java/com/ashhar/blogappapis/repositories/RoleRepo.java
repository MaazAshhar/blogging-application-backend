package com.ashhar.blogappapis.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashhar.blogappapis.entities.Role;

public interface RoleRepo extends JpaRepository<Role, UUID> {
	public Role findByName(String name);
	public boolean existsByName(String name);
}
