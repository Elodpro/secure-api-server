package com.serweb.secure_api_server.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController// Dit à Spring que cette classe gère les requêtes HTTP (API)
@RequestMapping("/api") // Pour l'url api/me ou api/admin/data ou autre
public class UserController {

    @GetMapping("/me") // Pour indiquer à Spring que cette méthode doit être exécutée quand un client envoie une requête GET vers l'URL /api/me
    public Map<String, String> getMe(Authentication authentication){

        String username = authentication.getName();
        String roles = authentication.getAuthorities().toString(); // Prend le(s) rôle(s) et le converti en chaîne de caractère

        return Map.of( // Inclut les 3 paires clés
                "message", "Accès protégé réussi. Jeton JWT validé",
                "username", username,
                "roles", roles
        );
    }

    @GetMapping("/admin/data") // Pour indiquer à Spring que cette méthode doit être exécutée quand un client envoie une requête GET vers l'URL /api/me
    @PreAuthorize("hasRole('ADMIN')") // Authorise seulement un rôle spécifique
    public Map<String, String> getAdminData(Authentication authentication){

        return Map.of(
                "message", "Données Admin à jour"
        );

    }

}
