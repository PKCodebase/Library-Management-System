package com.Library_Management_System.controller;

import com.Library_Management_System.entity.User;
import com.Library_Management_System.exception.UserNotFoundException;
import com.Library_Management_System.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

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
      assertEquals(user, result.getBody());
    }
    @Test
    public void testGetUserByIdNotFound() {
        when(userService.getUserById(1L)).thenReturn(null);
        assertThrows(UserNotFoundException.class, () -> userController.getUserById(2L));
    }

    @Test
    public void updateUserById(){
        User user = new User();
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
        assertThrows(UserNotFoundException.class, () -> userController.updateUserById(2L, user));
    }
}
