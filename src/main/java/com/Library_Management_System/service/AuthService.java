package com.Library_Management_System.service;

import com.Library_Management_System.Util.JwtUtil;
import com.Library_Management_System.entity.User;
import com.Library_Management_System.exception.InvalidEmailException;
import com.Library_Management_System.exception.InvalidPasswordException;
import com.Library_Management_System.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {


    @Autowired
    private  AuthRepository authRepository;


    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private  JwtUtil jwtUtil;


    public void registerUser(User user) {
        if (authRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        authRepository.save(user);
    }


    public String loginUser(String email, String password) throws InvalidEmailException, InvalidPasswordException {
        Optional<User> user = authRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new InvalidEmailException("Incorrect email");
        }

        if (!passwordEncoder.matches(password, user.get().getPassword())) {
            throw new InvalidPasswordException("Incorrect password");
        }

        return jwtUtil.generateToken(user.get().getEmail(), user.get().getRole().toString());
    }
}
