package com.wipro.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wipro.userservice.dto.LoginRequestDto;
import com.wipro.userservice.dto.UserRequestDto;
import com.wipro.userservice.service.UserService;

@RestController
@RequestMapping("/user-w")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequestDto userRequestDto) {
        String message = userService.createUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserRequestDto userRequestDto) {
        String message = userService.updateUser(id, userRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequestDto loginRequestDto) {
        Boolean result = userService.authUser(loginRequestDto.getEmail(), loginRequestDto.getPassword());

        if (result) {
            return ResponseEntity.status(HttpStatus.OK).body("Authentication Successful");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Authentication Failed");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.logoutUser());
    }
}