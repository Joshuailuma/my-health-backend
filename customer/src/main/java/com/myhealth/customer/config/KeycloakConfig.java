package com.myhealth.customer.config;


import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.KeycloakBuilder.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class KeycloakConfig {
    @Value("${keycloak.admin-client-id}")
    private String adminClientId;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.admin-client-secret}")
    private String secretKey;
    @Value("${keycloak.domain}")
    private String domain;

    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(domain)
                .realm(realm).
                grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(adminClientId)
                .clientSecret(secretKey)
                .build();
    }
}
