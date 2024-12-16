package com.Library_Management_System.service;

import com.Library_Management_System.Util.JwtUtil;
import com.Library_Management_System.entity.User;
import com.Library_Management_System.enums.UserRole;
import com.Library_Management_System.exception.InvalidEmailException;
import com.Library_Management_System.exception.InvalidPasswordException;
import com.Library_Management_System.repository.AuthRepository;
import com.Library_Management_System.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;



    @Autowired
    private UserService userService;


    @Autowired
    private  AuthRepository authRepository;


    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private  JwtUtil jwtUtil;


    public void registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        // Default role is USER
        if (user.getRole() == null) {
            user.setRole(UserRole.USER);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public String loginUser(String email, String password) throws InvalidEmailException, InvalidPasswordException {
        Optional<User> user = authRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new InvalidEmailException("Incorrect email");
        }

        if (!passwordEncoder.matches(password, user.get().getPassword())) {
            throw new InvalidPasswordException("Incorrect password");
        }
        return jwtUtil.generateToken(user.get().getEmail(), Collections.singletonList(user.get().getRole().name()));
    }
}
