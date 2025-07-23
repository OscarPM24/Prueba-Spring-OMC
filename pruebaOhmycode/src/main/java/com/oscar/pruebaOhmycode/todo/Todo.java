package com.oscar.pruebaOhmycode.todo;

import com.oscar.pruebaOhmycode.user.User;
import jakarta.persistence.*;

@Entity
@Table(name="todo")
public class Todo {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(nullable=false, length=30)
    private String title;

    @Column(nullable=false)
    private boolean completed;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user", nullable = false)
    private User user;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
