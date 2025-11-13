package com.serweb.secure_api_server.controller;

import com.serweb.secure_api_server.dto.LoginRequest;
import com.serweb.secure_api_server.service.JwtService;

import com.serweb.secure_api_server.service.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class AuthController {
/**/
    /**
     * Point de terminaison pour l'authentification (login).
     * Le client enverra ses identifiants (login/mdp) dans cette classe.
     */

    // Déclare le "cerveau" "L'AuthenticationManager"
    // ON met "final" pour qu'il soit obligatoire dès le début
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    // Injecte le "cerveau" via le constructeur
    // Spring va voir cela et va nous donner l'AuthenticationManager qu'on a défini dans SecurityConfig
    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/auth/login") // C'est un POST, pas un GET
    // Le @RequestBody dit à Spring de prendre le JSON du corps de la requête
    // LoginRequest loginRequest dit à Spring de convertir ce JSON en un objet "LoginRequest" et qu'il
    // l'appelle "loginRequest"
    public Map<String, String> login(@RequestBody LoginRequest loginRequest) {
        // On va utiliser le "cerveau" pour l'authentifier
        // Ceci comprend le mot de passe de Postman, le Hacher avec BCrypt
        // et le comparer à celui qu'on a défini pour "alice" dans SecurityConfig
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        // Variable qui stock le nom de l'user
        String username = authentication.getName();
        // Variable qui stock le résultat de l'appel au service
        String token = jwtService.generateToken(authentication);

        // Si les informations de connexion sont bonne (user et mdp correcte), ça va renvoyer ce qu'il
        // y a ci-dessous.
        return Map.of(
                "Authentification", "Utilisateur '" + username + "' authentifié avec succès!",
                "Token", token
        );
    }
}