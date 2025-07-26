package com.oscar.pruebaOhmycode.todo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Integer> {

    Page<Todo> findByTitleContainingIgnoreCase(String title, Pageable pageable); // Filters the todos that contain the title, ignore case
    Page<Todo> findByUser_Username(String username, Pageable pageable); // Filters the todos by exact username
    Page<Todo> findAll(Pageable pageable); // Gets all todos

}