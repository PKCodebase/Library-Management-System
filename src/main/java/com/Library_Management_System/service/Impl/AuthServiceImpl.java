package com.Library_Management_System.service.Impl;

import com.Library_Management_System.Util.JwtUtil;
import com.Library_Management_System.entity.User;
import com.Library_Management_System.enums.UserRole;
import com.Library_Management_System.exception.InvalidEmailException;
import com.Library_Management_System.exception.InvalidPasswordException;
import com.Library_Management_System.repository.AuthRepository;
import com.Library_Management_System.repository.UserRepository;
import com.Library_Management_System.service.AuthService;
import com.Library_Management_System.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void registerUser(User user) {
        if (authRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exist.");
        }

        if(authRepository.findByPhone(user.getPhone()).isPresent()){
            throw new IllegalArgumentException("Phone number already exist.");
        }

        // Default role is USER
        if (user.getRole() == null) {
            user.setRole(UserRole.USER);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        authRepository.save(user);
    }

    @Override
    public String loginUser(String email, String password) throws InvalidEmailException, InvalidPasswordException {
        Optional<User> user = authRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new InvalidEmailException("Incorrect email.");
        }

        if (!passwordEncoder.matches(password, user.get().getPassword())) {
            throw new InvalidPasswordException("Incorrect password.");
        }
        return jwtUtil.generateToken(user.get().getEmail(), Collections.singletonList(user.get().getRole().name()));
    }
}
