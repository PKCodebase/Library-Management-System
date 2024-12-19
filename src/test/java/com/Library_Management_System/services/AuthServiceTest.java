package com.Library_Management_System.services;


import com.Library_Management_System.entity.User;
import com.Library_Management_System.repository.AuthRepository;
import com.Library_Management_System.service.AuthService;
import com.Library_Management_System.service.Impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AuthServiceTest {

    @InjectMocks
    private AuthServiceImpl authServiceImpl;

    @Mock
    private AuthRepository authRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private User user;

    @Test
    public void registerUserTest() {


    }

}
