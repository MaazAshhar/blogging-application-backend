package com.ashhar.blogappapis.services;

import com.ashhar.blogappapis.payloads.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto, String userId);
    UserDto getUserById(String userId);
    List<UserDto> getAllUser();
    void deleteUser(String userId);
}
