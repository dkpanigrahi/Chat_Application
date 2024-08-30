package com.chat.service;

import com.chat.entity.User;
import com.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Fetch all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Fetch all users except the one with the specified ID
    public List<User> getAllUsersExcept(int userId) {
        return userRepository.findAllByIdNot(userId);
    }

    // Find a user by email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
