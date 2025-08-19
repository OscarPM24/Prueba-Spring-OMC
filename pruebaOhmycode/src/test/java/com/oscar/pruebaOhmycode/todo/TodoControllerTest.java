package com.oscar.pruebaOhmycode.todo;

import com.oscar.pruebaOhmycode.user.Address;
import com.oscar.pruebaOhmycode.user.User;
import com.oscar.pruebaOhmycode.user.UserDTO;
import com.oscar.pruebaOhmycode.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.data.domain.Page.empty;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class)
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TodoService todoService;

    @MockitoBean
    private UserService userService;

    @Test
    @WithMockUser(username = "oscar434", roles = "USER")
    void redirectToList() throws Exception { // Test to redirect to the list page from root URL
        mockMvc.perform(get("/")) // Perform a GET request to the root URL
                .andExpect(status().is3xxRedirection()) // Expect a 3xx redirection
                .andExpect(redirectedUrl("/list?page=0")); // Expect the correct redirected URL
    }

    @Test
    @WithMockUser(username = "oscar434", roles = "USER")
    void listTodosEmpty() throws Exception { // Test to load the empty list of todos
        given(todoService.findAllDTO(any())).willReturn(empty()); // Check that the TodoService returns an empty list

        mockMvc.perform(get("/list")) // Perform a GET request to the list
                .andExpect(status().isOk()) // Expect a 200 OK status
                .andExpect(view().name("list")) // Expect the view to be "list"
                .andExpect(model().attributeExists("todos")) // Expect the model "todos" to exist
                .andExpect(model().attribute("page", 0)); // Expect the model "page" to be 0
    }

    @Test
    @WithMockUser(username = "oscar434", roles = "USER")
    void listTodosWithData() throws Exception { // Test to load the list of todos
        Address mockAddress = new Address("Carrer 123", "Barcelona", "España", "08001"); // Create a mock Address
        UserDTO mockUser = new UserDTO(1, "Oscar",  "oscar434", mockAddress, Collections.emptyList()); // Create a mock User
        given(userService.findDTOById(1)).willReturn(Optional.of(mockUser)); // Check that the User exists
        TodoDTO mockTodo = new TodoDTO(1, "Test Todo", false, mockUser.getUsername(), mockUser.getAddress().getCountry()); // Create a mock Todo
        given(todoService.findDTOById(1)).willReturn(Optional.of(mockTodo)); // Check that the Todo exists

        given(todoService.findAllDTO(any())).willReturn(new PageImpl<>(List.of(mockTodo))); // Check that the TodoService returns a page with the mock Todo

        mockMvc.perform(get("/list")) // Perform a GET request to the list
                .andExpect(status().isOk()) // Expect a 200 OK status
                .andExpect(view().name("list")) // Expect the view to be "list"
                .andExpect(model().attributeExists("todos")) // Expect the model "todos" to exist
                .andExpect(model().attribute("page", 0)); // Expect the model "page" to be 0
    }

    @Test
    @WithMockUser(username = "oscar434", roles = "USER")
    void createForm() throws Exception { // Test to load the create todo form
        UserDTO mockUser = new UserDTO(1, "Oscar",  "oscar434", null, Collections.emptyList()); // Create a mock User
        given(userService.findDTOByUsername("oscar434")).willReturn(Optional.of(mockUser)); // Check that the User exists

        mockMvc.perform(get("/create")) // Perform a GET request to the create form
                .andExpect(status().isOk()) // Expect a 200 OK status
                .andExpect(view().name("todo_form")) // Expect the view to be "todo_form"
                .andExpect(model().attributeExists("todo")) // Expect the model "todo" to exist
                .andExpect(model().attribute("edit", false)); // Expect the model "edit" to be false
    }

    @Test
    @WithMockUser(username = "oscar434", roles = "USER")
    void createTodo() throws Exception { // Test to create a new todo
        User mockUser = new User(1, "oscar434", "password", "Oscar", null, Collections.emptyList()); // Create a mock User
        given(userService.findById(1)).willReturn(Optional.of(mockUser)); // Check that the User exists

        mockMvc.perform(post("/create") // Perform a POST request to create a new Todo
                .param("title", "New Todo") // Set the title of the Todo
                .param("user.id", "1") // Set the user ID
                .param("completed", "false").with(csrf())) // Set the completed status, with CSRF token
                .andExpect(status().is3xxRedirection()) // Expect a 3xx redirection
                .andExpect(redirectedUrl("/list?page=0&success=true")); // Expect the correct redirected URL
    }

    @Test
    @WithMockUser(username = "oscar434", roles = "USER")
    void editForm() throws Exception { // Test to load the edit todo form
        TodoDTO mockTodo = new TodoDTO(1, "Test Todo", false, null, null); // Create a mock Todo
        given(todoService.findDTOById(1)).willReturn(Optional.of(mockTodo)); // Check that the Todo exists
        UserDTO mockUser = new UserDTO(1, "Oscar",  "oscar434", null, Collections.emptyList()); // Create a mock User
        given(userService.findDTOByUsername("oscar434")).willReturn(Optional.of(mockUser)); // Check that the User exists

        mockMvc.perform(get("/edit/1")) // Perform a GET request to the edit form
                .andExpect(status().isOk()) // Expect a 200 OK status
                .andExpect(view().name("todo_form")) // Expect the view to be "todo_form"
                .andExpect(model().attributeExists("todo")) // Expect the model "todo" to exist
                .andExpect(model().attribute("edit", true)); // Expect the model "edit" to be true
    }

    @Test
    @WithMockUser(username = "oscar434", roles = "USER")
    void updateTodo() throws Exception { // Test to update an existing todo
        User mockUser = new User(1, "oscar434", "password", "Oscar", null, Collections.emptyList()); // Create a mock User
        given(userService.findById(1)).willReturn(Optional.of(mockUser)); // Check that the User exists

        mockMvc.perform(post("/edit/1") // Perform a POST request to update the Todo
                .param("title", "Todo Editado") // Set the title of the Todo
                .param("user.id", "1").with(csrf())) // Set the user ID, with CSRF token
                .andExpect(status().is3xxRedirection()) // Expect a 3xx redirection
                .andExpect(redirectedUrl("/list?page=0&success=true")); // Expect the correct redirected URL
    }

    @Test
    @WithMockUser(username = "oscar434", roles = "USER")
    void deleteTodo() throws Exception { // Test to delete a todo
        Todo mockTodo = new Todo(1, "Test Todo", false, null); // Create a mock Todo
        given(todoService.findById(1)).willReturn(Optional.of(mockTodo)); // Check that the Todo exists

        mockMvc.perform(post("/delete/1").with(csrf())) // Perform a POST request to delete the Todo, with CSRF token
                .andExpect(status().is3xxRedirection()) // Expect a 3xx redirection
                .andExpect(redirectedUrl("/list?page=0&success=true")); // Expect the correct redirected URL
    }
}