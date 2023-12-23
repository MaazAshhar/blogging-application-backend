package com.ashhar.blogappapis.controllers;

import com.ashhar.blogappapis.payloads.ApiResponse;
import com.ashhar.blogappapis.payloads.UserDto;
import com.ashhar.blogappapis.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/users")
public class UserController {
    @Autowired
    private UserService userService;

    // Post- create User;

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto createdUser=this.userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // Put-update user;

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") String uid){
        UserDto updatedUser=this.userService.updateUser(userDto,uid);
        //return new ResponseEntity<>(updatedUser,HttpStatus.OK);
        // another method to write above line of code;
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);

    }
    // Delete- user;

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") String uid){
        this.userService.deleteUser(uid);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted",true),HttpStatus.OK);
    }

    // Get - get all user;

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUser(){
        return ResponseEntity.ok(this.userService.getAllUser());
    }

    // Get - ge user by Id
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable() String userId){
        UserDto getUser=this.userService.getUserById(userId);
        return new ResponseEntity<>(getUser,HttpStatus.OK);
    }

}
