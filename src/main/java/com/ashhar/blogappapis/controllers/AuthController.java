package com.ashhar.blogappapis.controllers;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashhar.blogappapis.entities.Role;
import com.ashhar.blogappapis.entities.User;
import com.ashhar.blogappapis.exceptions.ApiException;
import com.ashhar.blogappapis.exceptions.DuplicateEmailException;
import com.ashhar.blogappapis.payloads.ApiResponse;
import com.ashhar.blogappapis.payloads.JwtAuthRequest;
import com.ashhar.blogappapis.payloads.JwtAuthResponse;
import com.ashhar.blogappapis.payloads.UserDto;
import com.ashhar.blogappapis.repositories.RoleRepo;
import com.ashhar.blogappapis.repositories.UserRepo;
import com.ashhar.blogappapis.security.JwtTokenHelper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(
			@RequestBody JwtAuthRequest request
			){
		this.authenticate(request.getUsername(),request.getPassword());
		UserDetails userDetails=this.userDetailService.loadUserByUsername(request.getUsername());
		
		String token=this.jwtTokenHelper.generateToken(userDetails);
		JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
		jwtAuthResponse.setToken(token);
		jwtAuthResponse.setUser(this.mapper.map((User)userDetails, UserDto.class));
		return new ResponseEntity<>(jwtAuthResponse,HttpStatus.OK);
	}
	
	private void authenticate(String username,String password) {
		UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username,password);
		try {
			this.authenticationManager.authenticate(authenticationToken);
		}catch(BadCredentialsException e) {
			throw new ApiException("Invalid username or password !!");
		}
	}
	
	@PostMapping("/signup")
	public ResponseEntity<ApiResponse> signup(@Valid @RequestBody UserDto userDto){
		Set<Role> rolesToAdd=new HashSet<>();
		if(roleRepo.existsByName("ROLE_USER")) {
			rolesToAdd.add(roleRepo.findByName("ROLE_USER"));
		}else {
			Role role = new Role();
			role.setName("ROLE_USER");
			Role savedRole=roleRepo.save(role);
			rolesToAdd.add(savedRole);
		}
		User user=this.mapper.map(userDto, User.class);
		user.setRoles(rolesToAdd);
		String hashPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(hashPassword);
		try {
			User savedUser=this.userRepo.save(user);
			ApiResponse apiResponse=new ApiResponse("registered successfully with id: "+savedUser.getId().toString(),true);
			return new ResponseEntity<>(apiResponse,HttpStatus.CREATED);
		}catch(DataIntegrityViolationException e) {
			throw new DuplicateEmailException("User", userDto.getEmail());
		}
	}
}
