package com.Library_Management_System.controller;

import com.Library_Management_System.Util.UserUtil;
import com.Library_Management_System.entity.User;
import com.Library_Management_System.exception.UserNotFoundException;
import com.Library_Management_System.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testGetAllUsers() {
        userController.getAllUsers();
    }

    @Test
    public void testGetUserById() {
      User user = new User();
      user.setId(1L);
      user.setName("Kaushik Prasad");

      when(userService.getUserById(1L)).thenReturn(user);
      ResponseEntity<User> result = userController.getUserById(1L);

      assertEquals(200, result.getStatusCode().value());
      assertNotNull(result.getBody());

    }
    @Test
    public void testGetUserByIdNotFound() {
        when(userService.getUserById(1L)).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> userController.getUserById(1L));
    }
    @Test
    public void updateUserById(){
        User user = UserUtil.userEntity();
        user.setId(1L);
        when(userService.updateUserById(1L, user)).thenReturn(user);
        ResponseEntity<User> result = userController.updateUserById(1L, user);
        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
        assertEquals(user, result.getBody());
    }

    @Test
    public void updateUserByIdNotFound(){
        User user = new User();
        user.setId(1L);

        when(userService.updateUserById(1L, user)).thenReturn(null);
        assertThrows(UserNotFoundException.class, () -> userController.updateUserById(1L, user));
    }
    @Test
    public void getUserByEmailSuccess(){
        User user = UserUtil.userEntity();
        user.setEmail("xyz@gmail.com");
        when(userService.getUserByEmail("xyz@gmail.com")).thenReturn(Optional.of(user));
        ResponseEntity<User> result = userController.getUserByEmail("xyz@gmail.com");
        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
        assertEquals(user, result.getBody());
    }
    @Test
    public void getUserByEmailNotFound(){
        when(userService.getUserByEmail("xyz@gmail.com")).thenReturn(Optional.empty());
        ResponseEntity<User> result = userController.getUserByEmail("xyz@gmail.com");
        assertEquals(404, result.getStatusCode().value());
        assertNull(result.getBody());
    }
    @Test
    public void getUserByPhoneSuccess(){
        User user = UserUtil.userEntity();
        user.setPhone(1234567890L);
        when(userService.getUserByPhone(1234567890L)).thenReturn(Optional.of(user));
        ResponseEntity<User> result = userController.getUserByPhone(1234567890L);
        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
        assertEquals(user, result.getBody());
    }
    @Test
    public void getUserByPhoneNotFound(){
        when(userService.getUserByPhone(1234567890L)).thenReturn(Optional.empty());
        ResponseEntity<User> result = userController.getUserByPhone(1234567890L);
        assertEquals(404, result.getStatusCode().value());
        assertNull(result.getBody());
    }
    @Test
    public void deleteUserByIdSuccess(){
        User user = UserUtil.userEntity();
        user.setId(1L);
        when(userService.getUserById(1L)).thenReturn(user);
        ResponseEntity<String> result = userController.deleteUserById(1L);
        assertEquals(200, result.getStatusCode().value());
        assertEquals("User deleted successfully", result.getBody());
    }
    @Test
    public void deleteUserByIdNotFound(){
        User user = UserUtil.userEntity();
        user.setId(1L);
        when(userService.getUserById(1L)).thenReturn(null);
        assertThrows(UserNotFoundException.class, () -> userController.deleteUserById(1L));
    }

}
