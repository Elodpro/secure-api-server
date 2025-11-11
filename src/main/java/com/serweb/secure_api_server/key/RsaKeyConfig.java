package com.serweb.secure_api_server.key;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

@Configuration // Dit à Spring : Ceci est une classe de fabrication, sans ca, le @Bean ne fonctionnera pas.
public class RsaKeyConfig {

    @Bean // Annotation qui dit à Spring: "Cette méthode fabrique un objet essentiel. Mets-le dans ton conteneur pour que je puisse l'injecter ailleurs."
    public KeyPair rsaKeyPair() throws Exception{

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048); // RSA de 2048 bits
        KeyPair stoKeyPair = kpg.generateKeyPair();// Génère cryptographiquement et stocke la paire de clés asymétriques (publique et privée) RSA de 2048 bits.
        return stoKeyPair;

    }

}
