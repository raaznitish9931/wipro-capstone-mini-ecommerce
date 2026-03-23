package com.wipro.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wipro.userservice.dto.UserResponseDto;
import com.wipro.userservice.service.UserService;

@RestController
@RequestMapping("/user-r")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/menu/{id}")
    public String getUserMenu(@PathVariable Long id) {
        return userService.getUserMenu(id);
    }
}