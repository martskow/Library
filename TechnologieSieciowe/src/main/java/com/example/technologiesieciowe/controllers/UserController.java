package com.example.technologiesieciowe.controllers;

import com.example.technologiesieciowe.infrastructure.entity.UserEntity;
import com.example.technologiesieciowe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserEntity addUser(@RequestBody UserEntity user){

        return userService.addUser(user);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void delete(@PathVariable Integer id) {

        userService.delete(id);
    }

    @GetMapping("/getAll")
    @ResponseStatus(code = HttpStatus.OK)
    public @ResponseBody Iterable<UserEntity> getAllBooks(){
        return userService.getAll();
    }

    @GetMapping("/getOne/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public UserEntity getOne (@PathVariable Integer id) {
        return userService.getOne(id);
    }

}

