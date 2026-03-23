package com.wipro.userservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.userservice.dto.UserResponseDto;
import com.wipro.userservice.entity.User;
import com.wipro.userservice.exception.ResourceNotFoundException;
import com.wipro.userservice.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserResponseDto> getAllUsers() {

        List<User> users = userRepository.findAll();
        List<UserResponseDto> responseList = new ArrayList<>();

        for (User user : users) {
            UserResponseDto responseDto = new UserResponseDto();
            responseDto.setId(user.getId());
            responseDto.setName(user.getName());
            responseDto.setEmail(user.getEmail());
            responseDto.setRole(user.getRole());
            responseDto.setAddress(user.getAddress());

            responseList.add(responseDto);
        }

        return responseList;
    }

    @Override
    public UserResponseDto getUserById(Long id) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }

        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(user.getId());
        responseDto.setName(user.getName());
        responseDto.setEmail(user.getEmail());
        responseDto.setRole(user.getRole());
        responseDto.setAddress(user.getAddress());

        return responseDto;
    }

    @Override
    public String getUserMenu(Long id) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }

        if (user.getRole().equalsIgnoreCase("ADMIN")) {
            return "Admin Menu: Add Product, Update Product, Delete Product, View Products";
        } else {
            return "Customer Menu: View Products, Add To Cart, Place Order, View Orders, Cancel Order";
        }
    }
}