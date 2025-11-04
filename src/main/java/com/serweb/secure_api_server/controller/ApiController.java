package com.serweb.secure_api_server.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "✅ Public: accessible sans authentification";
    }

    @GetMapping("/private")
    public String privateEndpoint(Authentication auth) {
        return "🔒 Private: bonjour " + auth.getName() + " (role USER)";
    }
}
