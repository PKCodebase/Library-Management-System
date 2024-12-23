package com.Library_Management_System.controller;

import com.Library_Management_System.entity.User;
import com.Library_Management_System.exception.UserNotFoundException;
import com.Library_Management_System.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/users")
@Tag(name = "User Management", description = "Endpoints for managing users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<User> getUserById(@PathVariable Long userId){

            User user = userService.getUserById(userId);
            if (user == null) {
                throw new UserNotFoundException("User not found with ID: " + userId);
            }
            return ResponseEntity.ok(user);

    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateUserById(@PathVariable Long userId, @RequestBody User userDetails){
        User updatedUser = userService.updateUserById(userId, userDetails);
        if (updatedUser == null) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/email")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
        return ResponseEntity.ok("User deleted successfully");
    }
    @GetMapping("/phone")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserByPhone(@RequestParam Long phone) {
        return userService.getUserByPhone(phone)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
