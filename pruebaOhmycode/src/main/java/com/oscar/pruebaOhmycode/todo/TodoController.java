package com.oscar.pruebaOhmycode.todo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class TodoController {

    private final TodoRepository todoRepository;
    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping("/list")
    public String list(
            @RequestParam(name="title", required = false) String title,
            @RequestParam(name="username", required = false) String username,
            @RequestParam(name="page", defaultValue = "0") int page,
            @RequestParam(name="sort", defaultValue = "id") String sort,
            @RequestParam(name="dir", defaultValue = "asc") String dir,
            Model model) {

        Sort sortOrder = dir.equalsIgnoreCase("asc") ? Sort.by(sort).ascending() : Sort.by(sort).descending();
        Pageable pageable = PageRequest.of(page, 10, sortOrder);
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
        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir);

        return "list";
    }


}
