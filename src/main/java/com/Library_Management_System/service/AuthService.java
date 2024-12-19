package com.Library_Management_System.service;


import com.Library_Management_System.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface AuthService   {


 void registerUser(User user);

 String loginUser(String email, String password);

}
