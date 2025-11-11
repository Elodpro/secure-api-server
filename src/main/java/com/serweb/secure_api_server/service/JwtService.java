package com.serweb.secure_api_server.service;

import jakarta.security.auth.message.callback.PrivateKeyCallback;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import javax.security.auth.Subject;

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

        // De la collection d'objets GrantedAuthority à une simple chaîne de caractère de rôles séparés par des espaces (scope)
        String scope = authentication.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.joining(""));

        // Contient les paramètres du jeton
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(authentication.getName()) // Extrait le nom d'utilisateur (Subject)
                .issuer("secure-api-server") // Définit l'émetteur (Issuer)
                .expiresAt(expiresAt)
                .claim("Scope", scope)
                .build();

        // Variable contenant le contenu du jeton qui est dans "claims" prêt à être signé cryptographiquement.
        // On utilise la méthode .from() pour dire d'où viens les paramètres du jeton
        JwtEncoderParameters parameters = JwtEncoderParameters.from(claims);

        // Emission du JWT signé pour l'utiliser dans la classe "login" pour le "return"
        return this.jwtEncoder.encode(parameters).getTokenValue();

    }

}
