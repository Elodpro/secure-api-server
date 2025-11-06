package com.serweb.secure_api_server.service;

import org.springframework.security.oauth2.jwt.JwtEncoder;

import com.serweb.secure_api_server.dto.LoginRequest;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

public class JwtService {
    // Déclaration qu'on ait besoin de cet outil
    private JwtEncoder jwtEncoder;

    public JwtService(JwtEncoder jwtEncoder) {
        // Spring le fournira
        this.jwtEncoder = jwtEncoder;
    }

    public Map<String, String> generateToken(Authentication authentication) {



    }

}
