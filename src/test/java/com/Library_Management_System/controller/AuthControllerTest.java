package com.Library_Management_System.controller;

import com.Library_Management_System.Util.UserUtil;
import com.Library_Management_System.entity.User;
import com.Library_Management_System.exception.IncorrectEmailException;
import com.Library_Management_System.exception.IncorrectPasswordException;
import com.Library_Management_System.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

   @Test
    public void getRegisterPageTestSuccess(){
        User user = UserUtil.userEntity();
        doNothing().when(authService).registerUser(user);

       ResponseEntity<String> response = (ResponseEntity<String>) authController.registerUser(user);

       assert(response.getStatusCode().is2xxSuccessful());
       verify(authService, times(1)).registerUser(user);

    }

    @Test
    public void getRegisterPageTestFailure(){
        User user = UserUtil.userEntity();
        doThrow(new IllegalArgumentException("Registration failed")).when(authService).registerUser(user);
        ResponseEntity<String> response = (ResponseEntity<String>) authController.registerUser(user);
        assert(response.getStatusCode().is4xxClientError());
        verify(authService, times(1)).registerUser(user);

    }
    @Test
    public void getLoginPageTestSuccess(){
       User user = UserUtil.userEntity();
        String token = "token";
        when(authService.loginUser(user.getEmail(),user.getPassword())).thenReturn(token);
        ResponseEntity<?> response = authController.loginUser(user.getEmail(), user.getPassword());
        assert(response.getStatusCode().is2xxSuccessful());
        assertEquals(token, response.getBody());
        verify(authService, times(1)).loginUser(user.getEmail(), user.getPassword());
    }
    @Test
    public void getLoginPageTestFailure(){
        User user = UserUtil.userEntity();
        when(authService.loginUser(user.getEmail(), user.getPassword())).thenThrow(new IllegalArgumentException("Login failed"));
        ResponseEntity<?> response = authController.loginUser(user.getEmail(), user.getPassword());
        assert(response.getStatusCode().is4xxClientError());
        verify(authService, times(1)).loginUser(user.getEmail(), user.getPassword());
    }

    @Test
    public void getLoginPageIncorrectEmailFailure(){
        User user = UserUtil.userEntity();
        when(authService.loginUser(user.getEmail(), user.getPassword())).thenThrow(new IncorrectEmailException("Incorrect email"));
        ResponseEntity<?> response = authController.loginUser(user.getEmail(), user.getPassword());
        assert(response.getStatusCode().is4xxClientError());
        assertEquals("Incorrect email", response.getBody());
        verify(authService, times(1)).loginUser(user.getEmail(), user.getPassword());
    }
    @Test
    public void getLoginPageIncorrectPasswordFailure(){
        User user = UserUtil.userEntity();
        when(authService.loginUser(user.getEmail(), user.getPassword())).thenThrow(new IncorrectPasswordException("Incorrect password"));
        ResponseEntity<?> response = authController.loginUser(user.getEmail(), user.getPassword());
        assert(response.getStatusCode().is4xxClientError());
        assertEquals("Incorrect password", response.getBody());
        verify(authService, times(1)).loginUser(user.getEmail(), user.getPassword());
    }

}
