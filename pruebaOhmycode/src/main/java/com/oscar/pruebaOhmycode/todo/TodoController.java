package com.oscar.pruebaOhmycode.todo;

import com.oscar.pruebaOhmycode.user.User;
import com.oscar.pruebaOhmycode.user.UserDTO;
import com.oscar.pruebaOhmycode.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
@Tag(name = "Todos", description = "Controller for managing todos")
public class TodoController {

    private final TodoService todoService;
    private final UserService userService;

    public TodoController(TodoService todoService, UserService userService) {
        this.todoService = todoService;
        this.userService = userService;
    }

    @GetMapping("/")
    @Operation(summary = "Redirect to list page", description = "Redirects to the list page from root URL")
    public String redirectToList() { // Redirects to the list page from root URL
        return "redirect:/list?page=0";
    }

    @GetMapping("/list")
    @Operation(summary = "List todos", description = "Loads the list of todos, depending on the filters applied")
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
        Page<TodoDTO> todos;

        if (title != null && !title.isEmpty()) { // Filter by title
            todos = todoService.findByTitle(title, pageable);
        } else if (username != null && !username.isEmpty()) { // Filter by username
            todos = todoService.findByUsername(username, pageable);
        } else { // No filter
            todos = todoService.findAllDTO(pageable);
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
    @Operation(summary = "Create todo form", description = "Loads the form to create todos")
    public String createForm(Authentication authentication, Model model) { // Loads the form to create todos
        String loggedUsername = authentication.getName(); // Gets the logged username
        System.out.println("Logged username: " + loggedUsername); // Debugging line to check the logged username
        UserDTO user = userService.findDTOByUsername(loggedUsername)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + loggedUsername)); // Gets the user that will be assigned to the todo
        model.addAttribute("todo", new Todo());
        model.addAttribute("user", user);
        model.addAttribute("edit", false);
        return "todo_form";
    }

    @PostMapping("/create")
    @Operation(summary = "Create todo", description = "Handles the creation of the todo")
    public String create(@ModelAttribute Todo todo, @RequestParam("user.id") int userId) { // Handles the creation of the todo
        User user = userService.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId)); // Gets the user that will be assigned to the todo
        todo.setUser(user);
        todoService.save(todo); // Saves the todo to the database
        return "redirect:/list?page=0&success=true";
    }

    @GetMapping("/edit/{id}")
    @Operation(summary = "Edit todo form", description = "Loads the form to edit todos")
    public String updateForm(@PathVariable int id, Authentication authentication, Model model) { // Loads the form to edit todos
        TodoDTO todo = todoService.findDTOById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid todo ID: " + id)); // Gets the todo that will be edited
        String loggedUsername = authentication.getName(); // Gets the logged username
        checkLoggedUser(id, loggedUsername);
        UserDTO user = userService.findDTOByUsername(loggedUsername)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + loggedUsername)); // Gets the user that will be assigned to the todo
        model.addAttribute("todo", todo);
        model.addAttribute("edit", true);
        model.addAttribute("user", user);
        return "todo_form";
    }

    @PostMapping("/edit/{id}")
    @Operation(summary = "Update todo", description = "Handles the update of the todo")
    public String update(@ModelAttribute Todo todo, @PathVariable int id, @RequestParam("user.id") int userId, Authentication authentication) { // Handles the update of the todo
        String loggedUsername = authentication.getName(); // Gets the logged username
        checkLoggedUser(id, loggedUsername); // Checks if the logged user is the owner of the todo
        User user = userService.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId)); // Gets the user that will be assigned to the todo
        Todo newTodo = new Todo(id, todo.getTitle(), todo.isCompleted(), user); // Creates a new todo with the updated values
        todoService.save(newTodo); // Saves the updated todo to the database
        return "redirect:/list?page=0&success=true";
    }

    @PostMapping("/delete/{id}")
    @Operation(summary = "Delete todo", description = "Handles the deletion of a todo")
    public String delete(@PathVariable int id, Authentication authentication) { // Handles the deletion of a todo
        Todo todo = todoService.findById(id)
                 .orElseThrow(() -> new IllegalArgumentException("Invalid todo ID: " + id)); // Gets the todo that will be deleted
        String loggedUsername = authentication.getName(); // Gets the logged username
        checkLoggedUser(id, loggedUsername); // Checks if the logged user is the owner of the todo
        todoService.delete(todo); // Deletes the todo from the database
        return "redirect:/list?page=0&success=true";
    }

    private void checkLoggedUser(int todoId, String loggedUsername) { // Checks if the logged user is the owner of the todo
        TodoDTO todo = todoService.findDTOById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid todo ID: " + todoId)); // Gets the todo that will be checked
        
        if (!todoService.checkLoggedUser(todo, loggedUsername)) { // Checks if the logged user is the owner of the todo
            throw new AccessDeniedException("You do not have permission to access this todo."); // Throws an exception if the user is not the owner
        }   
    }
}