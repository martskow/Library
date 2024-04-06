package com.example.technologiesieciowe.service;




import com.example.technologiesieciowe.infrastructure.entity.UserEntity;
import com.example.technologiesieciowe.infrastructure.repository.UserRepository;
import com.example.technologiesieciowe.service.error.FieldRequiredException;
import com.example.technologiesieciowe.service.error.UserErrors.UserAlreadyExistsException;
import com.example.technologiesieciowe.service.error.UserErrors.UserEmailExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserEntity addUser(UserEntity user) {
        if (user.getUserName() == null) {
            throw FieldRequiredException.create("User name");
        } else if (user.getUserPassword() == null) {
            throw FieldRequiredException.create("Password");
        } else if (user.getEmail() == null) {
            throw FieldRequiredException.create("Email");
        } else if (user.getUserFirstName() == null) {
            throw FieldRequiredException.create("First Name");
        } else if (user.getUserLastName() == null) {
            throw FieldRequiredException.create("Last Name");
        }

        Optional<UserEntity> existingUser = Optional.ofNullable(userRepository.getByUserName(user.getUserName()));
        if (existingUser.isPresent()) {
            throw UserAlreadyExistsException.create(user.getUserName());
        }

        Optional<UserEntity> existingEmail = Optional.ofNullable(userRepository.getByEmail(user.getEmail()));
        if (existingEmail.isPresent()) {
            throw UserEmailExistsException.create(user.getEmail());
        }

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