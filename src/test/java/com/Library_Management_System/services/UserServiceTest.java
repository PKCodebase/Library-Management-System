package com.Library_Management_System.services;

import com.Library_Management_System.entity.User;
import com.Library_Management_System.enums.UserRole;
import com.Library_Management_System.repository.UserRepository;
import com.Library_Management_System.service.Impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private User user;

    @Test
    public void getAllUsers(){
        User user1 = new User();
        user1.setId(1L);
        user1.setName("John Doe");
        user1.setEmail("john.doe@example.com");
        user1.setPassword("password123");
        user1.setRole(UserRole.USER);


        User user2 = new User();
        user2.setId(2L);
        user2.setName("John Doe");
        user2.setEmail("john.doe@example.com");
        user2.setPassword("password123");
        user2.setRole(UserRole.ADMIN);

        List<User> userList = new ArrayList<>();

        userList.add(user1);
        userList.add(user2);
        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userServiceImpl.getAllUsers();
        assert(result.size() == 2);
        assert(result.get(0).getName().equals("John Doe"));
        assert(result.get(1).getName().equals("John Doe"));
        assert(result.get(0).getEmail().equals("john.doe@example.com"));
        assert(result.get(1).getEmail().equals("john.doe@example.com"));
        assert(result.get(0).getPassword().equals("password123"));
        assert(result.get(1).getPassword().equals("password123"));
        assert(result.get(0).getRole().equals(UserRole.USER));
        assert(result.get(1).getRole().equals(UserRole.ADMIN));
        assert(result.get(0).getId() == 1L);
        assert(result.get(1).getId() == 2L);
        assert(result.get(0).getId() != result.get(1).getId());
    }
    @Test
    public void getUserById(){
        User user1 = new User();
        user1.setId(1L);
        user1.setName("John Doe");
        user1.setEmail("john.doe@example.com");
        user1.setPassword("password123");
        user1.setRole(UserRole.USER);

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user1));

        User result = userServiceImpl.getUserById(1L);
        assert(result.getName().equals("John Doe"));
        assert(result.getEmail().equals("john.doe@example.com"));
        assert(result.getPassword().equals("password123"));
        assert(result.getRole().equals(UserRole.USER));
        assert(result.getId() == 1L);
    }

    @Test
    public void updateUserById(){
        
    }
}
