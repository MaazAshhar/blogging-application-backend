package com.ashhar.blogappapis.services.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ashhar.blogappapis.entities.Role;
import com.ashhar.blogappapis.entities.User;
import com.ashhar.blogappapis.exceptions.DuplicateEmailException;
import com.ashhar.blogappapis.exceptions.IllegalUUIDException;
import com.ashhar.blogappapis.exceptions.ResourceNotFoundException;
import com.ashhar.blogappapis.payloads.UserDto;
import com.ashhar.blogappapis.payloads.UserUpdateDto;
import com.ashhar.blogappapis.repositories.RoleRepo;
import com.ashhar.blogappapis.repositories.UserRepo;
import com.ashhar.blogappapis.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	
    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    ModelMapper modelMapper;
    @Override
    public UserDto createUser(UserDto userDto) {
    	Set<Role> rolesToAdd=new HashSet<>();
    	Set<Role> rolesProvided=userDto.getRoles();
    	for(Iterator<Role> iterator = rolesProvided.iterator();iterator.hasNext();) {
    		Role role = iterator.next();
    		if(this.roleRepo.existsByName(role.getName())) {
    			rolesToAdd.add(this.roleRepo.findByName(role.getName()));
    		}else {
    			Role savedRole=this.roleRepo.save(role);
    			rolesToAdd.add(savedRole);
    		}
    	}
        User user=this.dtoToUser(userDto);
        String hashPassword=passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        try{
        	user.setRoles(rolesToAdd);;
            User savedUser= userRepo.save(user);
            return this.userToUserDto(savedUser);
        } catch(DataIntegrityViolationException e) {
            throw new DuplicateEmailException("User", userDto.getEmail());
        }
    }

    @Override
    public UserDto updateUser(UserUpdateDto userDto, String userId) {
        UUID userIds = null;
        try {
            userIds = UUID.fromString(userId);
        } catch (IllegalArgumentException e) {
            throw new IllegalUUIDException();
        }
        UUID finalUserIds = userIds;
        User user=this.userRepo.findById(userIds).orElseThrow(()->new ResourceNotFoundException("User","Id", finalUserIds));
        if(userDto.getName()!=null) {
        	user.setName(userDto.getName());
        }
        if(userDto.getEmail()!=null ) {
        	user.setEmail(userDto.getEmail());
        }
        if(userDto.getPassword()!=null) {
        	String hashPassword=passwordEncoder.encode(userDto.getPassword());
        	user.setPassword(hashPassword);
        }
        if(userDto.getPhone()!=null) {
        	user.setPhone(userDto.getPhone());
        }
        if(userDto.getAbout()!=null ) {
        	user.setAbout(userDto.getAbout());
        }
        if(userDto.getCity()!=null) {
        	user.setCity(userDto.getCity());
        }
        // save updated user
        User updatedUser=this.userRepo.save(user);
        return this.userToUserDto(updatedUser);
    }

    @Override
    public UserDto getUserById(String userId) {
        UUID userIds = null;
        try {
            userIds = UUID.fromString(userId);
        } catch (IllegalArgumentException e) {
            throw new IllegalUUIDException();
        }
        UUID finalUserIds = userIds;
        User user=this.userRepo.findById(userIds).orElseThrow(()->new ResourceNotFoundException("User","Id", finalUserIds));
        return this.userToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> allUsers=this.userRepo.findAll();
        List<UserDto> userDtos=allUsers.stream().map((user->userToUserDto(user))).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(String userId) {
        UUID userIds = null;
        try {
            userIds = UUID.fromString(userId);
        } catch (IllegalArgumentException e) {
            throw new IllegalUUIDException();
        }
        UUID finalUserIds = userIds;
        User user=this.userRepo.findById(userIds).orElseThrow(()->new ResourceNotFoundException("User","Id", finalUserIds));
        this.userRepo.delete(user);
    }

    public User dtoToUser(UserDto userDto){
        User user= modelMapper.map(userDto,User.class);
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setPhone(userDto.getPhone());
//        user.setAbout(userDto.getAbout());
//        user.setCity(userDto.getCity());
        return user;
    }
    public UserDto userToUserDto(User user){
        UserDto userDto= modelMapper.map(user,UserDto.class);
        return userDto;
    }
}
