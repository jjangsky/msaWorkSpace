package com.example.userserivce.service;

import com.example.userserivce.dto.UserDto;
import com.example.userserivce.jpa.UserEntity;


public interface UserService /*extends UserDetailsService */{
    UserDto createUser(UserDto userDto);

    UserDto getUserByUserId(String userId);

    Iterable<UserEntity> getUserByAll();
}
