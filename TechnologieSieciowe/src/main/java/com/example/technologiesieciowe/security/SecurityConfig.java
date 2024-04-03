package com.example.technologiesieciowe.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // w bazie danych role zapisuje się ROLE_NAZWA
    // w konfiguracji role zapisuje się NAZWA
    @Value("${jwt.token.key}")
    private String key;

    @Bean
    SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JWTTokenFilter(key), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(
                        authorizationManagerRequestMatcherRegistry ->
                                authorizationManagerRequestMatcherRegistry
                                        .requestMatchers("/login").permitAll()
                                        .requestMatchers("/book/getAll").hasAnyRole("ADMIN", "LIBRARIAN", "USER")
                                        .requestMatchers("/book/add").hasAnyRole("ADMIN", "LIBRARIAN")
                                        .requestMatchers("/book/delete").hasAnyRole("ADMIN", "LIBRARIAN")
                                        .requestMatchers("/user/add").hasAnyRole("ADMIN", "LIBRARIAN")
                                        .requestMatchers("/user/delete").hasRole("ADMIN")
                                        .requestMatchers("/user/getAll").hasRole("ADMIN")
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

}

