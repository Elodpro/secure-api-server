package com.serweb.secure_api_server.controller;

import com.serweb.secure_api_server.dto.LoginRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    // Déclare le "cerveau" "L'AuthenticationManager"
    private final AuthenticationManager authenticationManager;

    // Injecte le "cerveau" via le constructeur
    // Spring va voir cela et va nous donner l'AuthenticationManager qu'on a défini dans SecurityConfig
    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/auth/login") // C'est un POST, pas un GET
    public Map<String, String> login(@RequestBody LoginRequest loginRequest) {
        // C'est ici qu'on va générer le JWT
        // On va utiliser le "cerveau" pour l'authentifier
        // Ceci comprend le mot de passe de Postman, le Hacher avec BCrypt
        // et le comparer à celui qu'on a défini pour "alice" dans SecurityConfig
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        String username = authentication.getName();

        return Map.of(
                "message", "Utilisateur '" + username + "' authentifié avec succès!",
                "jwt_status", "Génération du JWT à venir.."
        );

    }
}