package com.serweb.secure_api_server.dto;

import jakarta.validation.constraints.NotBlank;


public class LoginRequest {
    @NotBlank(message = "Le nom de l'utilisateur est obligatoire") // Condition d'obligation
    private String username;
    @NotBlank(message = "Le mot de passe est obligatoire") // Condition d'obligation
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
