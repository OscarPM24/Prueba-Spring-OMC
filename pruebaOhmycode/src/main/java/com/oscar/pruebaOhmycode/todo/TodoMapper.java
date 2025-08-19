package com.oscar.pruebaOhmycode.todo;

public class TodoMapper { // Mapper class to convert between Todo and TodoDTO
    public static TodoDTO toDTO(Todo todo) { 
        if (todo == null) { return null; }
        return new TodoDTO(
                todo.getId(),
                todo.getTitle(),
                todo.isCompleted(),
                todo.getUser().getUsername(),
                todo.getUser().getAddress().getCountry()
        );
    }
}