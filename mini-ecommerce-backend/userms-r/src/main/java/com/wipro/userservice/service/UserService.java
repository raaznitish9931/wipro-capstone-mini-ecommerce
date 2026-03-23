package com.wipro.userservice.service;

import java.util.List;

import com.wipro.userservice.dto.UserResponseDto;

public interface UserService {

    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserById(Long id);

    String getUserMenu(Long id);
}