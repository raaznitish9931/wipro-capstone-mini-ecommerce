
package com.wipro.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wipro.userservice.dto.LoginRequestDto;
import com.wipro.userservice.dto.UserRequestDto;
import com.wipro.userservice.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/user-w")
@Tag(name = "User Write APIs", description = "Operations related to user creation, update, delete and authentication")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @Operation(summary = "Create User", description = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<String> createUser(@RequestBody UserRequestDto userRequestDto) {
        String message = userService.createUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update User", description = "Update user details by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserRequestDto userRequestDto) {
        String message = userService.updateUser(id, userRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete User", description = "Delete user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
    }

    @PostMapping("/login")
    @Operation(summary = "Login User", description = "Authenticate user using email and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful"),
            @ApiResponse(responseCode = "403", description = "Authentication failed")
    })
    public ResponseEntity<String> loginUser(@RequestBody LoginRequestDto loginRequestDto) {
        Boolean result = userService.authUser(loginRequestDto.getEmail(), loginRequestDto.getPassword());

        if (result) {
            return ResponseEntity.status(HttpStatus.OK).body("Authentication Successful");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Authentication Failed");
        }
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout User", description = "Logout the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logout successful")
    })
    public ResponseEntity<String> logoutUser() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.logoutUser());
    }
}