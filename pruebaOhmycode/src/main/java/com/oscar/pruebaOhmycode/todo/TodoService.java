package com.oscar.pruebaOhmycode.todo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public void save(Todo todo) { // Saves todo
        todoRepository.save(todo);
    }

    public void delete(Todo todo) { // Deletes todo
        todoRepository.delete(todo);
    }

    public Optional<Todo> findById(Integer id) { // Find todo by ID
        return todoRepository.findById(id);
    }

    public Optional<TodoDTO> findDTOById(Integer id) { // Find todo by ID
        return todoRepository.findById(id) 
            .map(todo -> TodoMapper.toDTO(todo)); // Maps Todo to TodoDTO
    }

    public Page<Todo> findAll(Pageable pageable) {  // Finds all todos with pagination
        return todoRepository.findAll(pageable);
    }

    public Page<TodoDTO> findAllDTO(Pageable pageable) {  // Finds all todos with pagination
        Page<Todo> todos = todoRepository.findAll(pageable);
        return todos.map(todo -> TodoMapper.toDTO(todo)); // Maps each Todo to TodoDTO
    }

    public Page<TodoDTO> findByTitle(String title, Pageable pageable) { // Finds todos containing title, ignoring case, with pagination
        Page<Todo> todos = todoRepository.findByTitleContainingIgnoreCase(title, pageable);
        return todos.map(todo -> TodoMapper.toDTO(todo)); // Maps each Todo to TodoDTO
    }

    public Page<TodoDTO> findByUsername(String username, Pageable pageable) { // Finds todos by username with pagination
        Page<Todo> todos = todoRepository.findByUser_Username(username, pageable);
        return todos.map(todo -> TodoMapper.toDTO(todo)); // Maps each Todo to TodoDTO
    }

    public boolean checkLoggedUser(TodoDTO todo, String loggedUsername) { // Checks if the logged user is the owner of the todo
        return todo.getUsername().equals(loggedUsername);
    }
}