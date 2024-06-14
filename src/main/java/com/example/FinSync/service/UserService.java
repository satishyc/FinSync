package com.example.FinSync.service;

import com.example.FinSync.entity.User;
import com.example.FinSync.entity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User registerUser(User user) {
        // Add custom business logic if needed (e.g., checking if email already exists)
        return userRepository.save(user);
    }
}
