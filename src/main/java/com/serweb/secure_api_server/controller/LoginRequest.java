package com.serweb.secure_api_server.controller;

public class LoginRequest {
    private String username;
    private String password;

    // Getter 'username'
    public String getUsername() {
        return username;
    }

    // Setter pour 'username'
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter pour 'password'
    public String getPassword() {
        return password;
    }

    // Setter pour 'password'
    public void setPassword(String password) {
        this.password = password;
    }
}
