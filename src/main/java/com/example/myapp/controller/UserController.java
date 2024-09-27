package com.example.myapp.controller;

import com.example.myapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.myapp.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/find/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username).orElse(null);
    }
}
