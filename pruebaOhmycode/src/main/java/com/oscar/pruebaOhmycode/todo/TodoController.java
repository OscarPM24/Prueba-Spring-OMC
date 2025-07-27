package com.oscar.pruebaOhmycode.todo;

import com.oscar.pruebaOhmycode.user.User;
import com.oscar.pruebaOhmycode.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class TodoController {

    private final TodoService todoService;
    private final UserService userService;

    public TodoController(TodoService todoService, UserService userService) {
        this.todoService = todoService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String redirectToList() { // Redirects to the list page from root URL
        return "redirect:/list?page=0";
    }

    @GetMapping("/list")
    public String list(
            @RequestParam(name="title", required = false) String title,
            @RequestParam(name="username", required = false) String username,
            @RequestParam(name="page", defaultValue = "0") int page,
            @RequestParam(name="sortField", defaultValue = "id") String sortField,
            @RequestParam(name="sortDir", defaultValue = "asc") String sortDir,
            Authentication authentication,
            Model model) { // Loads the list of todos, depending on the filters applied

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending(); // Sorts the todos by field and direction
        Pageable pageable = PageRequest.of(page, 10, sort); // 10 items per page
        Page<Todo> todos;

        if (title != null && !title.isEmpty()) { // Filter by title
            todos = todoService.findByTitle(title, pageable);
        } else if (username != null && !username.isEmpty()) { // Filter by username
            todos = todoService.findByUsername(username, pageable);
        } else { // No filter
            todos = todoService.findAll(pageable);
        }

        String loggedUsername = authentication.getName(); // Gets the logged username, to handle the edit/delete buttons on list.html
        model.addAttribute("todos", todos);
        model.addAttribute("page", page);
        model.addAttribute("title", title);
        model.addAttribute("username", username);
        model.addAttribute("loggedUsername", loggedUsername);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equalsIgnoreCase("asc") ? "desc" : "asc"); // Reverse sort direction
        return "list";
    }

    @GetMapping("/create")
    public String createForm(Authentication authentication, Model model) { // Loads the form to create todos
        String loggedUsername = authentication.getName(); // Gets the logged username
        User user = userService.findByUsername(loggedUsername)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + loggedUsername)); // Gets the user that will be assigned to the todo
        model.addAttribute("todo", new Todo());
        model.addAttribute("user", user);
        model.addAttribute("edit", false);
        return "todo_form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Todo todo, @RequestParam("user.id") int userId) { // Handles the creation of the todo
        User user = userService.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId)); // Gets the user that will be assigned to the todo
        todo.setUser(user);
        todoService.save(todo); // Saves the todo to the database
        return "redirect:/list?page=0&success=true";
    }

    @GetMapping("/edit/{id}")
    public String updateForm(@PathVariable int id, Authentication authentication, Model model) { // Loads the form to edit todos
        Todo todo = todoService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid todo ID: " + id)); // Gets the todo that will be edited
        String loggedUsername = authentication.getName(); // Gets the logged username
        User user = userService.findByUsername(loggedUsername)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + loggedUsername)); // Gets the user that will be assigned to the todo
        model.addAttribute("todo", todo);
        model.addAttribute("edit", true);
        model.addAttribute("user", user);
        return "todo_form";
    }

    @PostMapping("/edit/{id}")
    public String update(@ModelAttribute Todo todo, @PathVariable int id, @RequestParam("user.id") int userId) { // Handles the update of the todo
        User user = userService.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId)); // Gets the user that will be assigned to the todo
        Todo newTodo = new Todo(id, todo.getTitle(), todo.isCompleted(), user); // Creates a new todo with the updated values
        todoService.save(newTodo); // Saves the updated todo to the database
        return "redirect:/list?page=0&success=true";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id) { // Handles the deletion of a todo
         Todo todo = todoService.findById(id)
                 .orElseThrow(() -> new IllegalArgumentException("Invalid todo ID: " + id)); // Gets the todo that will be deleted
         todoService.delete(todo); // Deletes the todo from the database
         return "redirect:/list?page=0&success=true";
    }
}