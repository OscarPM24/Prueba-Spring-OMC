package com.oscar.pruebaOhmycode.user;

import java.util.List;

import com.oscar.pruebaOhmycode.todo.TodoDTO;

public class UserDTO {

    private int id;
    private String name;
    private String username;
    private Address address;
    private List<TodoDTO> todos;

    public UserDTO(int id, String name, String username, Address address, List<TodoDTO> todos) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.address = address;
        this.todos = todos;
    }

    public UserDTO() {

    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getUsername() { return username; }
    public Address getAddress() { return address; }
    public List<TodoDTO> getTodos() { return todos; }
}
