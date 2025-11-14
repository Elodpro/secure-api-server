package com.serweb.secure_api_server;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Création de l'instance de l'outil de cryptage du mdp et le retourner.
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder){ // Déclaration d'utilisateurs

        UserDetails aliceUser = User.withUsername("Alice") // L'utilisateur Alice
                .password(passwordEncoder.encode("Passw0rd")) // Hache ce mot de passe
                .roles("USER") // Rôle USER
                .build();

        UserDetails elodUser = User.withUsername("Elod") // L'utilisateur Elod
                .password(passwordEncoder.encode("Pa$$w0rd")) // Hache ce mot de passe
                .roles("ADMIN", "USER") // Rôles ADMIN et USER
                .build();

        return new InMemoryUserDetailsManager(aliceUser, elodUser);// Crée un annuaire d'utilisateur et le renvoi
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder){ // Fabrique l'objet AuthenticationManager que le AuthController demande

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(); // Création du spécialiste

        authProvider.setUserDetailsService(userDetailsService); // La configuration pour les deux paramètre de la méthode "authenticationManager"
        authProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authProvider); // On renvoit le Manager final

    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){

        JwtAuthenticationConverter converter = new JwtAuthenticationConverter(); // Le traducteur principal

        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter(); // L'extracteur

        authoritiesConverter.setAuthoritiesClaimName("scope"); // Le champ qui devra lire dans le jeton

        authoritiesConverter.setAuthorityPrefix(""); // On lui dit de ne pas ajouter de prefix

        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter); // On demande au traducteur d'utiliser l'extracteur

        return converter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtDecoder jwtDecoder, OAuth2ResourceServerProperties oAuth2ResourceServerProperties, JwtAuthenticationConverter converter) throws Exception {
        return http
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.decoder(jwtDecoder).jwtAuthenticationConverter(converter)))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/public/**",
                                "/auth/login",
                                "/actuator/health"
                        ).permitAll()
                        .requestMatchers("/actuator/**").hasRole("ADMIN")
                        .requestMatchers("/api/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated())
                .build();
    }
}


