package com.todo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.todo.dto.TodoRequest;
import com.todo.dto.TodoResponse;
import com.todo.entity.User;
import com.todo.repository.UserRepository;
import com.todo.service.TodoService;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;
    private final UserRepository userRepository;

    public TodoController(TodoService todoService,
                          UserRepository userRepository) {
        this.todoService = todoService;
        this.userRepository = userRepository;
    }

    @PostMapping("/create-todo")
    public ResponseEntity<TodoResponse> createTodo(
            @RequestBody TodoRequest request) {

        User user = getAuthenticatedUser();

        return ResponseEntity.ok(
                todoService.createTodo(request, user));
    }

    @GetMapping("/get-todo")
    public ResponseEntity<List<TodoResponse>> getAllTodos() {

        User user = getAuthenticatedUser();

        return ResponseEntity.ok(
                todoService.getAllTodos(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> getTodoById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                todoService.getTodoById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> updateTodo(
            @PathVariable Long id,
            @RequestBody TodoRequest request) {

        return ResponseEntity.ok(
                todoService.updateTodo(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo(
            @PathVariable Long id) {

        todoService.deleteTodo(id);

        return ResponseEntity.ok("Todo Deleted Successfully");
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<TodoResponse> completeTodo(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                todoService.markCompleted(id));
    }

    @PatchMapping("/{id}/incomplete")
    public ResponseEntity<TodoResponse> incompleteTodo(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                todoService.markIncomplete(id));
    }

    // ─── Private Helper ─────────────────────────────────────────────────

    private User getAuthenticatedUser() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Authenticated user not found"));
    }
}