package com.oscar.pruebaOhmycode.todo;

import com.oscar.pruebaOhmycode.user.User;
import com.oscar.pruebaOhmycode.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class TodoController {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    public TodoController(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String redirectToList() {
        return "redirect:/list?page=0";
    }

    @GetMapping("/list")
    public String list(
            @RequestParam(name="title", required = false) String title,
            @RequestParam(name="username", required = false) String username,
            @RequestParam(name="page", defaultValue = "0") int page,
            Model model) {

        Pageable pageable = PageRequest.of(page, 10);
        Page<Todo> todos;

        if (title != null && !title.isEmpty()) { // Filter by title
            todos = todoRepository.findByTitleContainingIgnoreCase(title, pageable);
        } else if (username != null && !username.isEmpty()) { // Filter by username
            todos = todoRepository.findByUser_Username(username, pageable);
        } else {
            todos = todoRepository.findAll(pageable);
        }

        model.addAttribute("todos", todos);
        model.addAttribute("page", page);
        model.addAttribute("title", title);
        model.addAttribute("username", username);
        return "list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("todo", new Todo());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("edit", false);
        return "todo_form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Todo todo, @RequestParam("user.id") int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));
        todo.setUser(user);
        todoRepository.save(todo);
        return "redirect:/list?page=0&success=true";
    }

    @GetMapping("/edit/{id}")
    public String updateForm(@PathVariable int id, Model model) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid todo ID: " + id));
        User user = todo.getUser();
        model.addAttribute("todo", todo);
        model.addAttribute("user", user);
        model.addAttribute("edit", true);
        model.addAttribute("users", userRepository.findAll());
        return "todo_form";
    }

    @PostMapping("/edit/{id}")
    public String update(@ModelAttribute Todo todo, @PathVariable int id, @RequestParam("user.id") int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));
        Todo newTodo = new Todo(id, todo.getTitle(), todo.isCompleted(), user);
        todoRepository.save(newTodo);
        return "redirect:/list?page=0&success=true";
    }

     @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
         Todo todo = todoRepository.findById(id)
                 .orElseThrow(() -> new IllegalArgumentException("Invalid todo ID: " + id));
         todoRepository.delete(todo);
         return "redirect:/list?page=0&success=true";
    }
}
