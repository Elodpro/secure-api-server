package com.serweb.secure_api_server.controller; // Assurez-vous que le nom du package est correct

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Contrôleur pour les points de terminaison publics,
 * ne nécessitant aucune authentification.
 */
@RestController // Dit à Spring que cette classe gère les requêtes HTTP (API)
public class StatusController {

    /**
     * Point de terminaison simple pour vérifier que l'API est vivante.
     * C'est la "Ressource publique".
     */
    @GetMapping("/status") // Lie l'URL GET /status à cette méthode
    public Map<String, String> getStatus() {
        // Spring convertira automatiquement cette Map en réponse JSON
        return Map.of("status", "UP", "message", "Welcome to the Secure API!");
    }
}