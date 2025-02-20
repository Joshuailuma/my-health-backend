package com.myhealth.customer.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.net.http.HttpHeaders;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {
    @Value("${keycloak.admin-client-id}")
    private String adminClientId;


    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        Converter jwtConverter = new KeycloakReactiveJwtConverter(adminClientId);


        return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(auth -> auth
                        .pathMatchers("/customer/auth/**").permitAll()
                        .pathMatchers("/customer/doctor/**").authenticated()
                        .pathMatchers("/customer/profile/**").authenticated()
                        .pathMatchers("customer/otp/**").permitAll()
                        .anyExchange().authenticated()
                ).oauth2ResourceServer(oAuth2ResourceServerSpec ->
                        oAuth2ResourceServerSpec.jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(
                                new ReactiveJwtAuthenticationConverterAdapter(jwtConverter)
                        )))
                .build();


    }

}
