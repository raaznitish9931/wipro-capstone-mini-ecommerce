// package com.wipro.userservice.controller;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;

// import com.wipro.userservice.dto.UserResponseDto;
// import com.wipro.userservice.service.UserService;

// @RestController
// @RequestMapping("/user-r")
// public class UserController {

//     @Autowired
//     private UserService userService;

//     @GetMapping
//     public List<UserResponseDto> getAllUsers() {
//         return userService.getAllUsers();
//     }

//     @GetMapping("/{id}")
//     public UserResponseDto getUserById(@PathVariable Long id) {
//         return userService.getUserById(id);
//     }

//     @GetMapping("/menu/{id}")
//     public String getUserMenu(@PathVariable Long id) {
//         return userService.getUserMenu(id);
//     }
// }



package com.wipro.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wipro.userservice.dto.UserResponseDto;
import com.wipro.userservice.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/user-r")
@Tag(name = "User Read APIs", description = "Operations related to fetching user data")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Get all users", description = "Fetch list of all users")
    @ApiResponse(responseCode = "200", description = "Users fetched successfully")
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Fetch user details using user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public UserResponseDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/menu/{id}")
    @Operation(summary = "Get user menu", description = "Fetch menu based on user role")
    @ApiResponse(responseCode = "200", description = "Menu fetched successfully")
    public String getUserMenu(@PathVariable Long id) {
        return userService.getUserMenu(id);
    }
}