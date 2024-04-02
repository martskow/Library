package com.example.technologiesieciowe.service;




import com.example.technologiesieciowe.infrastructure.entity.UserEntity;
import com.example.technologiesieciowe.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity addUser(UserEntity user) {
        String hashedPassword = passwordEncoder.encode(user.getUserPassword());
        user.setUserPassword(hashedPassword);
        return userRepository.save(user);
    }

    public Iterable<UserEntity> getAll(){
        return userRepository.findAll();
    }
    public UserEntity getOne(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found."));
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

}