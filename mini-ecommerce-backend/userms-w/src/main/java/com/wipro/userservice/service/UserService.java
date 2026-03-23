package com.wipro.userservice.service;

import com.wipro.userservice.dto.UserRequestDto;

public interface UserService {

    String createUser(UserRequestDto userRequestDto);

    String updateUser(Long id, UserRequestDto userRequestDto);

    void deleteUser(Long id);

    boolean authUser(String email, String password);

    String logoutUser();
}