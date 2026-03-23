package com.wipro.userservice.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.userservice.dto.UserRequestDto;
import com.wipro.userservice.entity.User;
import com.wipro.userservice.exception.ResourceNotFoundException;
import com.wipro.userservice.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public String createUser(UserRequestDto userRequestDto) {

        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(userRequestDto.getName());
        user.setEmail(userRequestDto.getEmail());

        String salt = BCrypt.gensalt(12);
        String hashPassword = BCrypt.hashpw(userRequestDto.getPassword(), salt);
        user.setPassword(hashPassword);

        user.setRole(userRequestDto.getRole());
        user.setAddress(userRequestDto.getAddress());

        userRepository.save(user);

        return "User Created Successfully";
    }

    @Override
    public String updateUser(Long id, UserRequestDto userRequestDto) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }

        user.setName(userRequestDto.getName());
        user.setEmail(userRequestDto.getEmail());

        String salt = BCrypt.gensalt(12);
        String hashPassword = BCrypt.hashpw(userRequestDto.getPassword(), salt);
        user.setPassword(hashPassword);

        user.setRole(userRequestDto.getRole());
        user.setAddress(userRequestDto.getAddress());

        userRepository.save(user);

        return "User Updated Successfully";
    }

    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }

        userRepository.delete(user);
    }

    @Override
    public boolean authUser(String email, String password) {

        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }

        String passwordInDb = user.getPassword();

        return BCrypt.checkpw(password, passwordInDb);
    }

    @Override
    public String logoutUser() {
        return "Logout successful";
    }
}