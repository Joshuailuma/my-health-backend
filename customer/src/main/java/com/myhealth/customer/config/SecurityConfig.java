package com.myhealth.customer.config;

import com.myhealth.library.enums.ROLE;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        http. authorizeExchange( auth -> auth
                        .pathMatchers("/customer/auth/**").permitAll()
                        .pathMatchers("/customer/doctor").authenticated()
                        .pathMatchers("customer/otp/").permitAll()


                );
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/customer/auth/**").permitAll() // Allow public access
//                        .requestMatchers("/admin/**").hasRole(String.valueOf(ROLE.ADMIN)) // Restrict to ADMIN role
//                        .requestMatchers("/customer/profile/**").hasAnyRole(String.valueOf(ROLE.PATIENT), String.valueOf(ROLE.DOCTOR)) // Restrict to USER or ADMIN roles
//                        .anyRequest().authenticated() // All other endpoints require authentication
//                );

        return http.build();
    }
}
