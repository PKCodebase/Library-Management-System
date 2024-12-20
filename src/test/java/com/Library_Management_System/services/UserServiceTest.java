package com.Library_Management_System.services;

import com.Library_Management_System.Util.UserUtil;
import com.Library_Management_System.entity.User;
import com.Library_Management_System.exception.NullValueException;
import com.Library_Management_System.exception.UserNotFoundException;
import com.Library_Management_System.repository.UserRepository;
import com.Library_Management_System.service.Impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.List;
import java.util.Optional;

import static com.Library_Management_System.Util.UserUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    private PasswordEncoder  passwordEncoder;


    private User user;

    @Test
    public void getAllUsers(){

        List<User> users = List.of(userEntity(),userEntity2(),adminEntity());
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userServiceImpl.getAllUsers();

        assertNotNull(result, "Result should not be null");
        assertEquals(3, result.size(), "Expected 3 users in the result");
        assertEquals("User", result.get(0).getName(), "First user's name should be 'User'");
        assertEquals("Admin", result.get(2).getName(), "Last user's name should be 'Admin'");

        verify(userRepository, times(1)).findAll();
    }
    @Test
    public void getAllUsersFailure(){
        when(userRepository.findAll()).thenThrow(new NullValueException("Database error"));

        try {
            userServiceImpl.getAllUsers();
            fail("Expected exception to be thrown");
        } catch (NullValueException e) {
            assertEquals("Database error", e.getMessage());
        }

    }

    @Test
    public void getUserByIdSuccess(){
        User user1 = UserUtil.userEntity();
        user1.setId(1L);
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user1));
        User result = userServiceImpl.getUserById(1L);
       verify(userRepository, times(1)).findById(1L);

    }

    @Test
    public void getUserByIdFailure(){
     User user1 = UserUtil.userEntity();
     userRepository.save(user1);
     assertThrows(UserNotFoundException.class, () -> userServiceImpl.getUserById(2L));
     verify(userRepository, times(1)).findById(2L);
    }
    @Test
    public void getUserByEmailNotFound(){
        User user1 = UserUtil.userEntity();
        user1.setEmail("xyz@gmail.com");
        userRepository.save(user1);
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.getUserByEmail("xyz@gmail.com"));
        verify(userRepository, times(1)).findByEmail("xyz@gmail.com");
    }
    @Test
    public void updatedUserSuccess() {
        User user1 = UserUtil.userEntity();
        user1.setId(1L);
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user1));
        when(userRepository.save(user1)).thenReturn(user1);
        userServiceImpl.updateUserById(1L, user1);
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user1);
    }




    @Test
    public void updateUserFailure(){
        User user1 = UserUtil.userEntity();
        user1.setId(1L);
        when(userRepository.findById(1L)).thenThrow(new UserNotFoundException("User not found"));
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.updateUserById(1L, user1));
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void getUserByEmailSuccess(){
        User user1 = UserUtil.userEntity();
        user1.setEmail("xyz@gmail.com");
        when(userRepository.findByEmail("xyz@gmail.com")).thenReturn(java.util.Optional.of(user1));
         userServiceImpl.getUserByEmail("xyz@gmail.com");
        verify(userRepository, times(1)).findByEmail("xyz@gmail.com");
    }

    @Test
    public void getUserByEmailFailure(){
        User user1 = UserUtil.userEntity();
        user1.setEmail("xyz@gmail.com");
        when(userRepository.findByEmail("xyz@gmail.com")).thenThrow(new UserNotFoundException("User not found"));
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.getUserByEmail("xyz@gmail.com"));
        verify(userRepository, times(1)).findByEmail("xyz@gmail.com");
    }

    @Test
    public void getUserByPhoneSuccess(){
        User user1 = UserUtil.userEntity();
        user1.setPhone(8767696898L);
        when(userRepository.findByPhone(8767696898L)).thenReturn(Optional.of(user1));
        userServiceImpl.getUserByPhone(8767696898L);
        verify(userRepository, times(1)).findByPhone(8767696898L);
    }

    @Test
    public void getUserByPhoneFailure(){
        User user1 = UserUtil.userEntity();
        user1.setPhone(8767696898L);
        when(userRepository.findByPhone(8767696898L)).thenThrow(new UserNotFoundException("User not found"));
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.getUserByPhone(8767696898L));
        verify(userRepository, times(1)).findByPhone(8767696898L);
    }

    @Test
    public void deleteUserByIdSuccess(){
        User user1 = UserUtil.userEntity();
        user1.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        userServiceImpl.deleteUserById(1L);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void deleteUserByIdFailure(){
        User user1 = UserUtil.userEntity();
        userRepository.save(user1);
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.deleteUserById(2L));
        verify(userRepository, times(1)).findById(2L);
    }



}
