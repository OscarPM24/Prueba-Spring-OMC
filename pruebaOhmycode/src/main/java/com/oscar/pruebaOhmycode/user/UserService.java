package com.oscar.pruebaOhmycode.user;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findById(Integer id) { // Finds user by ID
        return userRepository.findById(id);
    }

    public Optional<UserDTO> findDTOById(Integer id) { // Finds user by ID and converts to UserDTO
        return userRepository.findById(id)
            .map(user -> UserMapper.toDTO(user)); // Maps User to UserDTO
    }

    public List<User> findAll() { // Finds all users
        return userRepository.findAll();
    }

    public List<UserDTO> findAllDTO() { // Finds all users and converts to UserDTO
        return userRepository.findAll()
            .stream() // Convert List<User> into Stream<User>
            .map(user -> UserMapper.toDTO(user)) // Transform User -> UserDTO
            .collect(Collectors.toList()); // Collect the stream back into a List<UserDTO>
    }

    public Optional<User> findByUsername(String username) { // Finds user by username
        return userRepository.findByUsername(username);
    }

    public Optional<UserDTO> findDTOByUsername(String username) { // Finds user by username and converts to UserDTO
        return userRepository.findByUsername(username)
            .map(user -> UserMapper.toDTO(user)); // Maps User to UserDTO
    }
}