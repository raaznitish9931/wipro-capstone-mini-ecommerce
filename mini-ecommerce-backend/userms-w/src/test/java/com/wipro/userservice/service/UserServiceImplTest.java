package com.wipro.userservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wipro.userservice.dto.UserRequestDto;
import com.wipro.userservice.entity.User;
import com.wipro.userservice.exception.ResourceNotFoundException;
import com.wipro.userservice.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private UserRequestDto userDto;
    private User user;

    @BeforeEach
    void setUp() {
        userDto = new UserRequestDto();
        userDto.setName("Nitish");
        userDto.setEmail("nitish@gmail.com");
        userDto.setPassword("1234");
        userDto.setRole("USER");
        userDto.setAddress("Bhopal");

        user = new User();
        user.setId(1L);
        user.setName("Nitish");
        user.setEmail("nitish@gmail.com");
        user.setPassword("1234");
        user.setRole("USER");
        user.setAddress("Bhopal");
    }

    // ✅ Test Create User
    @Test
    void testCreateUser() {
        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        String result = userServiceImpl.createUser(userDto);

        assertEquals("User Created Successfully", result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    // ✅ Test Update User
    @Test
    void testUpdateUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        String result = userServiceImpl.updateUser(1L, userDto);

        assertEquals("User Updated Successfully", result);
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(any(User.class));
    }

    // ✅ Test Update User - Not Found
    @Test
    void testUpdateUser_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            userServiceImpl.updateUser(1L, userDto);
        });
    }

    // ✅ Test Delete User
    @Test
    void testDeleteUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);

        userServiceImpl.deleteUser(1L);

        verify(userRepository, times(1)).delete(user);
    }

    // ✅ Test Delete User - Not Found
    @Test
    void testDeleteUser_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            userServiceImpl.deleteUser(1L);
        });
    }
}