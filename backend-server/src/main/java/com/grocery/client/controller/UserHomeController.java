package com.grocery.client.controller;

import com.grocery.client.entity.User;
import com.grocery.client.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
public class UserHomeController {

    @Autowired
    UserService userService;

    @GetMapping("/hey")
    public String hey() {
        System.out.println("Hey");
        return "Hey";
    }

    @PostMapping(value = "signup")
    public ResponseEntity<Map<String , String>> signUpUser(@RequestBody User user) {
        System.out.println(user.getId());
        System.out.println(user.getFirstName());
        System.out.println(user.getLastName());
        System.out.println(user.getEmail());
        System.out.println("SignUp Received");
        if(userService.searchUser(user)) {
            return new ResponseEntity<>(
                    Map.of("message","User "  + user.getFirstName() + " already exists"),
                    HttpStatus.BAD_REQUEST
            );
        }
        userService.createUser(user);
        System.out.println("User created");
        return new ResponseEntity<>(
                Map.of("message","User " + user.getFirstName() + " created successfully"),
                HttpStatus.CREATED
        );
    }

    @PostMapping(value = "login")
    public ResponseEntity<Map<String,String>> logInUser(@RequestBody User user) {
        System.out.println("Login entered");
        System.out.println(user.getEmail());
        System.out.println(user.getPassword());
        if(userService.searchUser(user)) {
            return new ResponseEntity<>(
                    Map.of("message", user.getEmail()+" Successfully logged in"),
                    HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
                Map.of("message", user.getEmail() + " not present"),
                HttpStatus.NOT_FOUND);
    }
}
