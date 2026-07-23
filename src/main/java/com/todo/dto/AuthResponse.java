package com.todo.dto;

public class AuthResponse {

    private int status;
    private String message;
    private String token;
    private UserInfo user;

    public AuthResponse() {
    }

    public AuthResponse(int status, String message,
                        String token, UserInfo user) {
        this.status = status;
        this.message = message;
        this.token = token;
        this.user = user;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }
}