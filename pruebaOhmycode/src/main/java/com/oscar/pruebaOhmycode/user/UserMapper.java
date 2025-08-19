package com.oscar.pruebaOhmycode.user;

import java.util.stream.Collectors;

import com.oscar.pruebaOhmycode.todo.TodoMapper;

public class UserMapper {

    // Convert User entity to UserDTO
    public static UserDTO toDTO(User user) {
        if (user == null) return null;

        return new UserDTO(
            user.getId(),
            user.getName(),
            user.getUsername(),
            user.getAddress(),
            user.getTodos()
                .stream() // Convert List<Todo> into Stream<Todo>
                .map(todo -> TodoMapper.toDTO(todo)) // Transform Todo -> TodoDTO
                .collect(Collectors.toList()) // Collect the stream back into a List<TodoDTO>
        );
    }
}