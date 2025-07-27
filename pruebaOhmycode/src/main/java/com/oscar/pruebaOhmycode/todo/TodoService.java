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

    public Optional<Todo> findById(Integer id) { // Finds todo by ID
        return todoRepository.findById(id);
    }

    public Page<Todo> findAll(Pageable pageable) {  // Finds all todos with pagination
        return todoRepository.findAll(pageable);
    }

    public Page<Todo> findByTitle(String title, Pageable pageable) { // Finds todos containing title, ignoring case, with pagination
        return todoRepository.findByTitleContainingIgnoreCase(title, pageable);
    }

    public Page<Todo> findByUsername(String username, Pageable pageable) { // Finds todos by username with pagination
        return todoRepository.findByUser_Username(username, pageable);
    }
}