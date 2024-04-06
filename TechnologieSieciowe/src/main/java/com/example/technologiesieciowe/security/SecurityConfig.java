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
                                        .requestMatchers("/book/getOne/{id}").hasAnyRole("ADMIN", "LIBRARIAN", "USER")
                                        .requestMatchers("/book/delete/{id}").hasAnyRole("ADMIN", "LIBRARIAN")
                                        .requestMatchers("/user/add").hasAnyRole("ADMIN", "LIBRARIAN")
                                        .requestMatchers("/user/delete/{id}").hasRole("ADMIN")
                                        .requestMatchers("/user/getAll").hasRole("ADMIN")
                                        .requestMatchers("/review/add").hasRole("USER")
                                        .requestMatchers("/review/edit/{id}").hasRole("USER")
                                        .requestMatchers("/review/getAll").hasAnyRole("ADMIN", "LIBRARIAN", "USER")
                                        .requestMatchers("review/delete/{id}").hasRole("ADMIN")
                                        .requestMatchers("/review/getOne/{id}").hasAnyRole("ADMIN", "LIBRARIAN", "USER")
                                        .requestMatchers("/review/getByBook/{bookId}").hasAnyRole("ADMIN", "LIBRARIAN", "USER")
                                        .requestMatchers("/loan/add").hasRole("LIBRARIAN")
                                        .requestMatchers("/loan/delete/{id}").hasRole("ADMIN")
                                        .requestMatchers("/loan/getAll").hasAnyRole("ADMIN", "LIBRARIAN")
                                        .requestMatchers("/loan/getOne/{id}").hasAnyRole("ADMIN", "LIBRARIAN", "USER")
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

}

