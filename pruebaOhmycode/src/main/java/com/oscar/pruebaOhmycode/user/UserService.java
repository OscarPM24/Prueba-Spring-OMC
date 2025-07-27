package com.oscar.pruebaOhmycode.user;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findById(Integer id) { // Finds user by ID
        return userRepository.findById(id);
    }

    public List<User> findAll() { // Finds all users
        return userRepository.findAll();
    }

    public Optional<User> findByUsername(String username) { // Finds user by username
        return userRepository.findByUsername(username);
    }
}