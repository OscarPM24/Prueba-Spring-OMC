package com.oscar.pruebaOhmycode.todo;

public class TodoDTO {
    private int id;
    private String title;
    private boolean completed;
    private String username;
    private String country;

    public TodoDTO(int id, String title, boolean completed, String username, String country) {
        this.id = id;
        this.title = title;
        this.completed = completed;
        this.username = username;
        this.country = country;
    }

    public TodoDTO() {

    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public boolean isCompleted() { return completed; }
    public String getUsername() { return username; }
    public String getCountry() { return country; }
}
