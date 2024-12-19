package com.Library_Management_System.Util;

import com.Library_Management_System.entity.User;
import com.Library_Management_System.enums.UserRole;

public class UserUtil {
    public static User userEntity(){
        User user = new User();
        user.setName("User");
        user.setEmail("xyz@gmail.com");
        user.setPassword("user123");
        user.setPhone(1234567890L);
        user.setRole(UserRole.USER);
        return user;
    }
    public static User userEntity2(){
        User user = new User();
        user.setName("User2");
        user.setEmail("xyz2@gmail.com");
        user.setPassword("user123");
        user.setPhone(1234567899L);
        return user;
    }
    public static User adminEntity(){
        User user = new User();
        user.setName("Admin");
        user.setEmail("");
        user.setPassword("admin123");
        user.setPhone(1234567890L);
        return user;
    }

}
