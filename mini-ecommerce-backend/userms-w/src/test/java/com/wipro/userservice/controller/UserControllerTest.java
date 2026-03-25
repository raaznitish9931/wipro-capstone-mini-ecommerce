package com.wipro.userservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.userservice.dto.LoginRequestDto;
import com.wipro.userservice.dto.UserRequestDto;
import com.wipro.userservice.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserRequestDto userRequestDto;
    private LoginRequestDto loginRequestDto;

    @BeforeEach
    void setUp() {
        userRequestDto = new UserRequestDto();
        userRequestDto.setName("Nitish");
        userRequestDto.setEmail("nitish@gmail.com");
        userRequestDto.setPassword("1234");
        userRequestDto.setRole("USER");
        userRequestDto.setAddress("Bhopal");

        loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail("nitish@gmail.com");
        loginRequestDto.setPassword("1234");
    }

    @Test
    void testCreateUser() throws Exception {
        when(userService.createUser(any(UserRequestDto.class)))
                .thenReturn("User Created Successfully");

        mockMvc.perform(post("/user-w")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("User Created Successfully"));
    }

    @Test
    void testUpdateUser() throws Exception {
        when(userService.updateUser(eq(1L), any(UserRequestDto.class)))
                .thenReturn("User Updated Successfully");

        mockMvc.perform(put("/user-w/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("User Updated Successfully"));
    }

    @Test
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/user-w/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully"));
    }

    @Test
    void testLoginUser_Success() throws Exception {
        when(userService.authUser(loginRequestDto.getEmail(), loginRequestDto.getPassword()))
                .thenReturn(true);

        mockMvc.perform(post("/user-w/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Authentication Successful"));
    }

    @Test
    void testLoginUser_Failure() throws Exception {
        when(userService.authUser(loginRequestDto.getEmail(), loginRequestDto.getPassword()))
                .thenReturn(false);

        mockMvc.perform(post("/user-w/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequestDto)))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Authentication Failed"));
    }

    @Test
    void testLogoutUser() throws Exception {
        when(userService.logoutUser()).thenReturn("Logout Successful");

        mockMvc.perform(post("/user-w/logout"))
                .andExpect(status().isOk())
                .andExpect(content().string("Logout Successful"));
    }
}