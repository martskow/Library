package com.example.technologiesieciowe.service;




import com.example.technologiesieciowe.infrastructure.entity.UserEntity;
import com.example.technologiesieciowe.infrastructure.repository.UserRepository;
import com.example.technologiesieciowe.service.error.FieldRequiredException;
import com.example.technologiesieciowe.service.error.UserErrors.UserAccessDeniedException;
import com.example.technologiesieciowe.service.error.UserErrors.UserAlreadyExistsException;
import com.example.technologiesieciowe.service.error.UserErrors.UserEmailExistsException;
import com.example.technologiesieciowe.service.error.UserErrors.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
        return userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.create(userId.toString()));
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    public UserEntity editUser(Integer userId, UserEntity editedUser) {
        UserEntity userToEdit = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.create(userId.toString()));

        String loggedInUsername = LoginService.getLoggedInUserId();
        String loggedInUserRole = LoginService.getLoggedInUserRole();
        if (!((loggedInUserRole.equals("ROLE_ADMIN") || loggedInUserRole.equals("ROLE_LIBRARIAN")) ||
                (userToEdit.getUserId().toString().equals(loggedInUsername)))) {
            throw UserAccessDeniedException.create("You are not allowed to edit information about this user.");
        }

        String newUserName = editedUser.getUserName();
        String newEmail = editedUser.getEmail();
        String newFirstName = editedUser.getUserFirstName();
        String newLastName = editedUser.getUserLastName();

        if (newUserName != null) {
            Optional<UserEntity> existingUser = Optional.ofNullable(userRepository.getByUserName(editedUser.getUserName()));
            if (existingUser.isPresent()) {
                throw UserAlreadyExistsException.create(userToEdit.getUserName());
            } else {
                userToEdit.setUserName(newUserName);
            }

        }
        if (newEmail != null) {
            Optional<UserEntity> existingEmail = Optional.ofNullable(userRepository.getByEmail(editedUser.getEmail()));
            if (existingEmail.isPresent()) {
                throw UserEmailExistsException.create(userToEdit.getEmail());
            } else {
                userToEdit.setEmail(newEmail);
            }
        }
        if (newFirstName != null) {
            userToEdit.setUserFirstName(newFirstName);
        }
        if (newLastName != null) {
            userToEdit.setUserLastName(newLastName);
        }

        return userRepository.save(userToEdit);
    }

    public UserEntity newUserPassword (Integer userId, UserEntity userToChangePassword) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> UserNotFoundException.create(userId.toString()));

        String loggedInUsername = LoginService.getLoggedInUserId();
        String loggedInUserRole = LoginService.getLoggedInUserRole();
        if (!(loggedInUserRole.equals("ROLE_ADMIN") || (userId.toString().equals(loggedInUsername)))) {
            throw UserAccessDeniedException.create("You are not allowed to change this user password.");
        }

        if (userToChangePassword.getUserPassword() == null) {
            throw FieldRequiredException.create("Password");
        } else {
            String newHashedPassword = passwordEncoder.encode(userToChangePassword.getUserPassword());
            user.setUserPassword(newHashedPassword);
        }

        return userRepository.save(user);
    }
}