package com.Library_Management_System.service.Impl;

import com.Library_Management_System.entity.User;
import com.Library_Management_System.exception.UserNotFoundException;
import com.Library_Management_System.repository.UserRepository;
import com.Library_Management_System.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public List<User> getAllUsers(){
        List<User> users = userRepository.findAll();
        if(users.isEmpty()){
            throw new UserNotFoundException("No users found");
        }
        return users;
    }
    @Override
    public User getUserById(Long userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            return user.get();
        }
        throw new UserNotFoundException("User not found with ID: " + userId);
    }
    @Override
    public User updateUserById(Long userId, User userDetails) {
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isEmpty()) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
        User updatedUser = existingUser.get();
        updatedUser.setName(userDetails.getName());
        updatedUser.setEmail(userDetails.getEmail());
        updatedUser.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        return userRepository.save(updatedUser);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        Optional<User> user =  userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw new UserNotFoundException("User not found with email: " + email);
        }
        return user;
    }
    @Override
    public void deleteUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            userRepository.deleteById(userId);
        } else {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
    }
    @Override
    public Optional<User> getUserByPhone(Long phone) {
        Optional<User> user =  userRepository.findByPhone(phone);
        if(user.isEmpty()){
            throw new UserNotFoundException("User not found with phone: " + phone);
        }
        return user;
    }
}
