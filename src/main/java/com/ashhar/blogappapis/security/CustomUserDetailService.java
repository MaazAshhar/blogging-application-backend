package com.ashhar.blogappapis.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ashhar.blogappapis.entities.User;
import com.ashhar.blogappapis.exceptions.EmailNotFoundException;
import com.ashhar.blogappapis.repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// loading user from database by username.
		User user=this.userRepo.findByEmail(username).orElseThrow(()-> new EmailNotFoundException("User","Email",username));
		
		return user;
	}

}
