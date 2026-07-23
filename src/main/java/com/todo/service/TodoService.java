package com.todo.service;

import java.util.List;

import com.todo.dto.TodoRequest;
import com.todo.dto.TodoResponse;
import com.todo.entity.User;

public interface TodoService {

    TodoResponse createTodo(TodoRequest request, User user);

    List<TodoResponse> getAllTodos(User user);

    TodoResponse getTodoById(Long id);

    TodoResponse updateTodo(Long id, TodoRequest request);

    void deleteTodo(Long id);

    TodoResponse markCompleted(Long id);

    TodoResponse markIncomplete(Long id);
}