package com.todo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.todo.dto.TodoRequest;
import com.todo.dto.TodoResponse;
import com.todo.entity.Todo;
import com.todo.entity.User;
import com.todo.repository.TodoRepository;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public TodoResponse createTodo(TodoRequest request, User user) {

        Todo todo = new Todo();

        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setDueDate(request.getDueDate());
        todo.setCompleted(false);
        todo.setUser(user);

        return mapToResponse(todoRepository.save(todo));
    }

    @Override
    public List<TodoResponse> getAllTodos(User user) {
        return todoRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TodoResponse getTodoById(Long id) {
        return mapToResponse(findTodoById(id));
    }

    @Override
    public TodoResponse updateTodo(Long id, TodoRequest request) {

        Todo todo = findTodoById(id);

        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setDueDate(request.getDueDate());

        return mapToResponse(todoRepository.save(todo));
    }

    @Override
    public void deleteTodo(Long id) {

        Todo todo = findTodoById(id);

        todoRepository.delete(todo);
    }

    @Override
    public TodoResponse markCompleted(Long id) {

        Todo todo = findTodoById(id);

        todo.setCompleted(true);

        return mapToResponse(todoRepository.save(todo));
    }

    @Override
    public TodoResponse markIncomplete(Long id) {

        Todo todo = findTodoById(id);

        todo.setCompleted(false);

        return mapToResponse(todoRepository.save(todo));
    }

    // ─── Private Helpers ────────────────────────────────────────────────

    private Todo findTodoById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Todo Not Found"));
    }

    private TodoResponse mapToResponse(Todo todo) {
        return new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.isCompleted(),
                todo.getDueDate(),
                todo.getUser().getId()
        );
    }
}