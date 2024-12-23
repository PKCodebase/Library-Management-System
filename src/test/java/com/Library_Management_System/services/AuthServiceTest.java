package com.Library_Management_System.services;


import com.Library_Management_System.Util.JwtUtil;
import com.Library_Management_System.Util.UserUtil;
import com.Library_Management_System.entity.User;
import com.Library_Management_System.enums.UserRole;
import com.Library_Management_System.exception.EmailAlreadyExistException;
import com.Library_Management_System.exception.IncorrectEmailException;
import com.Library_Management_System.exception.IncorrectPasswordException;
import com.Library_Management_System.exception.PhoneAlreadyExistException;
import com.Library_Management_System.repository.AuthRepository;
import com.Library_Management_System.service.AuthService;
import com.Library_Management_System.service.Impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @InjectMocks
    private AuthServiceImpl authServiceImpl;

    @Mock
    private AuthRepository authRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil  jwtUtil;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private User user;

    //Register Test cases
    @Test
    public void registerSuccess() {
        User user = UserUtil.userEntity();

        when(authRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(authRepository.findByPhone(user.getPhone())).thenReturn(Optional.empty());
        when(passwordEncoder.encode("user123")).thenReturn("encodedPassword");

        authServiceImpl.registerUser(user);

        assertEquals("encodedPassword", user.getPassword());
        assertEquals(UserRole.USER, user.getRole());

        verify(authRepository).findByEmail(user.getEmail());
        verify(authRepository).findByPhone(user.getPhone());
        verify(passwordEncoder).encode("user123");

        verify(authRepository).save(user);
    }
    @Test
    public void registerEmailAlreadyExist() {
        User user = UserUtil.userEntity();
        when(authRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        assertThrows(EmailAlreadyExistException.class, () -> authServiceImpl.registerUser(user));
        verify(authRepository).findByEmail(user.getEmail());

    }
    @Test
    public void registerPhoneAlreadyExist(){
        User user = UserUtil.userEntity();

        when(authRepository.findByPhone(user.getPhone())).thenReturn(Optional.of(user));
        assertThrows(PhoneAlreadyExistException.class, () -> authServiceImpl.registerUser(user));
        verify(authRepository).findByPhone(user.getPhone());
    }

    //Login Test cases
    @Test
    public void loginSuccess() {
        User user = UserUtil.userEntity();
        when(authRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("user123", user.getPassword())).thenReturn(true);
        when(jwtUtil.generateToken(user.getEmail(), Collections.singletonList(user.getRole().name()))).thenReturn("token");


        String token = authServiceImpl.loginUser(user.getEmail(), "user123");
        assertEquals("token", token);


        verify(authRepository).findByEmail(user.getEmail());
        verify(passwordEncoder).matches("user123", user.getPassword());
        verify(jwtUtil).generateToken(user.getEmail(), Collections.singletonList(user.getRole().name()));
    }

    @Test
    public void incorrectEmail(){
        User user = UserUtil.userEntity();

        when(authRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        assertThrows(IncorrectEmailException.class, () -> authServiceImpl.loginUser(user.getEmail(), "user123"));

        verify(authRepository).findByEmail(user.getEmail());
    }

    @Test
    public void incorrectPassword(){
        User user = UserUtil.userEntity();

        when(authRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("user123", user.getPassword())).thenReturn(false);
        assertThrows(IncorrectPasswordException.class, () -> authServiceImpl.loginUser(user.getEmail(), "user123"));

        verify(authRepository).findByEmail(user.getEmail());
        verify(passwordEncoder).matches("user123", user.getPassword());
    }
}
