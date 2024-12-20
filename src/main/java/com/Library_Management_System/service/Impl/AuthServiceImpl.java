package com.Library_Management_System.service.Impl;

import com.Library_Management_System.Util.JwtUtil;
import com.Library_Management_System.entity.User;
import com.Library_Management_System.enums.UserRole;
import com.Library_Management_System.exception.EmailAlreadyExistException;
import com.Library_Management_System.exception.IncorrectEmailException;
import com.Library_Management_System.exception.IncorrectPasswordException;
import com.Library_Management_System.exception.PhoneAlreadyExistException;
import com.Library_Management_System.repository.AuthRepository;
import com.Library_Management_System.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
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
            throw new EmailAlreadyExistException("Email already exist.");
        }

        if(authRepository.findByPhone(user.getPhone()).isPresent()){
            throw new PhoneAlreadyExistException("Phone number already exist.");
        }

        // Default role is USER
        if (user.getRole() == null) {
            user.setRole(UserRole.USER);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        authRepository.save(user);
    }

    @Override
    public String loginUser(String email, String password) throws IncorrectEmailException, IncorrectPasswordException {
        Optional<User> user = authRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new IncorrectEmailException("Incorrect email.");
        }

        if (!passwordEncoder.matches(password, user.get().getPassword())) {
            throw new IncorrectPasswordException("Incorrect password.");
        }
        return jwtUtil.generateToken(user.get().getEmail(), Collections.singletonList(user.get().getRole().name()));
    }
}
