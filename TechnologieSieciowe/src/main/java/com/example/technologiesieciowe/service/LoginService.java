package com.example.technologiesieciowe.service;

import com.example.technologiesieciowe.LoginForm;
import com.example.technologiesieciowe.infrastructure.entity.UserEntity;
import com.example.technologiesieciowe.infrastructure.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Date;

@Service
public class LoginService {
    private final PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @Value("${jwt.token.key}")
    private String key;

    @Autowired
    public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public String userLogin(LoginForm loginForm) throws AuthenticationException {
        UserEntity user = userRepository.getByUserName(loginForm.getLogin());
        if (passwordEncoder.matches(loginForm.getPassword(), user.getUserPassword())){
            long timeMillis = System.currentTimeMillis();
            String token = Jwts.builder()
                    .issuedAt(new Date(timeMillis))
                    .expiration(new Date(timeMillis+60*60*1000))
                    .claim("id", user.getUserId())
                    .claim("role", user.getRole())
                    .signWith(SignatureAlgorithm.HS256, key)
                    .compact();
            return token;
        } else {
            throw new AuthenticationException("Incorrect password") {};
        }
    }
}

