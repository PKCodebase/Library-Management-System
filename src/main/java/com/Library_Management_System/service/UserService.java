package com.Library_Management_System.service;

import com.Library_Management_System.Util.JwtUtil;
import com.Library_Management_System.entity.User;
import com.Library_Management_System.exception.UserNotFoundException;
import com.Library_Management_System.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface UserService {


     List<User> getAllUsers();

    User getUserById(Long userId) throws UserNotFoundException;

     User updateUserById(Long userId,  User userDetails) throws UserNotFoundException;

    Optional<User> getUserByEmail(String email);
    Optional<User> getUserByPhone(Long phone);
   void deleteUserById(Long userId) throws UserNotFoundException;
}
