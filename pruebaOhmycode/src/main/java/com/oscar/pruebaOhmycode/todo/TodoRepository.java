package com.oscar.pruebaOhmycode.todo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {
    Page<Todo> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<Todo> findByUser_Username(String username, Pageable pageable);
}