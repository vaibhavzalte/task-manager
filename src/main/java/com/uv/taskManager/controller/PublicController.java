package com.uv.taskManager.controller;

import com.uv.taskManager.entity.User;
import com.uv.taskManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    UserService userService;
    @GetMapping("/health-check")
    public String healthCheck(){
        return "ALL GOOD";
    }

    @PostMapping("/create-user")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        try {
            userService.saveNewUser(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
