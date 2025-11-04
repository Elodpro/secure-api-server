package com.serweb.secure_api_server.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Contrôleur dédié à l'authentification
 * C'est ici que le client viendra demander un jeton JWT
 *
 **/

@RestController
public class AuthController {
/**/
    /**
     * Point de terminaison pour l'authentification (login).
     * Le client enverra ses identifiants (login/mdp) ici.
     */

    @PostMapping("/auth/login") // C'est un POST, pas un GET
    public Map<String, String> login() {
        // C'est ici qu'on va générer le JWT
        return Map.of("message", "Login Successful. JWT en attente !");
    }
}