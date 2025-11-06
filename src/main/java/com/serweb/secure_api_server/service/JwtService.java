package com.serweb.secure_api_server.service;

import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;


@Service
public class JwtService {
    // Déclaration qu'on ait besoin de cet outil
    private JwtEncoder jwtEncoder;

    public JwtService(JwtEncoder jwtEncoder) {
        // Spring le fournira
        this.jwtEncoder = jwtEncoder;
    }

    public String generateToken(Authentication authentication) {

        Instant now = Instant.now(); // Récupère l'heure actuelle
        Instant expiresAt = now.plus(1, ChronoUnit.HOURS); // Prends l'heure actuelle de la variable "now" et rajoute 1h avec la méthode "plus()" ce qui donne l'expiration

        String scope = authentication.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.joining("")); // de la collection d'objets GrantedAuthority à une simple chaîne de rôles séparés par des espaces (scope)

        return "Expire dans : " + expiresAt + scope; // Retournera un String vide

    }

}
