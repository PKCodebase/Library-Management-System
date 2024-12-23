package com.Library_Management_System.controller;

import com.Library_Management_System.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AuthControllerTest {
    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;
    
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }
}
