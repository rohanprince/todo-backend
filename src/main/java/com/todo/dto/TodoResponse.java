package com.todo.dto;

import java.time.LocalDate;

public class TodoResponse {

    private Long id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDate dueDate;
    private Long userId;

    public TodoResponse() {
    }

    public TodoResponse(Long id, String title,
                        String description,
                        boolean completed,
                        LocalDate dueDate,
                        Long userId) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.dueDate = dueDate;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Long getUserId() {
        return userId;
    }
}