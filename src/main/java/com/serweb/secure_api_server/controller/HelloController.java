package com.serweb.secure_api_server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  // Indique à Spring que cette classe gère des requêtes HTTP
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "👋 Bonjour depuis votre serveur Spring Boot !";
    }
}
