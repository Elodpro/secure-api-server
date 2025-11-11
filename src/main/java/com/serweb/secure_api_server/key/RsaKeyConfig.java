package com.serweb.secure_api_server.key;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtEncoder;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@Configuration // Dit à Spring : Ceci est une classe de fabrication, sans ca, le @Bean ne fonctionnera pas.
public class RsaKeyConfig {

    @Bean // Annotation qui dit à Spring: "Cette méthode fabrique un objet essentiel. Mets-le dans ton conteneur pour que je puisse l'injecter ailleurs."
    public KeyPair rsaKeyPair() throws Exception{

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048); // RSA de 2048 bits
        KeyPair stoKeyPair = kpg.generateKeyPair();// Génère cryptographiquement et stocke la paire de clés asymétriques (publique et privée) RSA de 2048 bits.
        return stoKeyPair;

    }

    @Bean
    public RSAKey rsaKey(KeyPair kp) throws Exception{

        // Aidé par l'IA car c'est que de la syntaxe et pas de la logique
        // Extrait les clés du KeyPair
        RSAPublicKey publicKey = (RSAPublicKey) kp.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) kp.getPrivate();

        // Utilise le Constructeur (Builder) pour formater la clé
        return new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())// Ajoute l'identifiant unique (Key ID)
                .build();// Finalise et retourne l'objet RSAKey

    }

    @Bean
    public JwtEncoder jwtEncoder(RSAKey rsaKey) throws Exception{

        new JWKSet(rsaKey); // NimbusJwtEncoder ne sait pas comment prendre le "Tampon secret" directement

        JWKSet contRsaKey = new JWKSet(rsaKey);

    }

}
