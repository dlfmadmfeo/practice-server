package com.example.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.demo.dto.user.UserCreateRequestDto;
import com.example.demo.dto.user.UserCreateResponseDto;
import com.example.demo.dto.user.UserLoginRequestDto;
import com.example.demo.dto.user.UserResponseDto;
import com.example.demo.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    
    // User -> UserResponse
    UserResponseDto userToUserResponse(User user);
    
    UserCreateRequestDto userToUserCreateRequest(User user);
    
    UserLoginRequestDto userToUserLoginRequest(User user);
    
    User userCreateRequestToUser(UserCreateRequestDto dto);
    
    UserCreateResponseDto userCreateResponseDto(User user);
}
