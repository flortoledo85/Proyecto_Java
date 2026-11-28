package com.talentotech.optica_backend.config;

import java.util.Base64;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;

@Configuration
public class JwtConfig {

    @Bean
    public SecretKey jwtKey(@Value("${JWT_SECRET}") String secreto) {
        byte[] bytes = Base64.getDecoder().decode(secreto);

        if (bytes.length < 32) {
            throw new IllegalArgumentException(
                "JWT_SECRET debe tener al menos 32 bytes"
            );
        }

        return new SecretKeySpec(bytes, "HmacSHA256");
    }

    @Bean
    public JwtEncoder jwtEncoder(SecretKey jwtKey) {
        return NimbusJwtEncoder.withSecretKey(jwtKey).build();
    }

    @Bean
    public JwtDecoder jwtDecoder(SecretKey jwtKey) {
        NimbusJwtDecoder decoder = NimbusJwtDecoder
            .withSecretKey(jwtKey)
            .macAlgorithm(MacAlgorithm.HS256)
            .build();

        decoder.setJwtValidator(
            JwtValidators.createDefaultWithIssuer("optica_backend")
        );

        return decoder;
    }
}