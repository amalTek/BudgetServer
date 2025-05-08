package com.bezkoder.spring.jpa.postgresql.controller;

import com.bezkoder.spring.jpa.postgresql.Request.LoginRequest;
import com.bezkoder.spring.jpa.postgresql.Service.UserService;
import com.bezkoder.spring.jpa.postgresql.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin()
@RestController
@RequestMapping("/api")public class UsersController {

    @Autowired
    UserService userService;

    @PostMapping("/addUser")
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PostMapping("/loginUser")
    public Boolean loginUser(@RequestBody LoginRequest loginRequest) {
        return userService.loginUser(loginRequest);

    }

}