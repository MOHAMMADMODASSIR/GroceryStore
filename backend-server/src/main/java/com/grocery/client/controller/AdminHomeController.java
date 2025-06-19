package com.grocery.client.controller;

import com.grocery.client.entity.Admin;
import com.grocery.client.entity.User;
import com.grocery.client.service.AdminService;
import com.grocery.client.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/admin")
public class AdminHomeController {

    @Autowired
    AdminService adminService;

    @GetMapping("/hey")
    public String hey() {
        System.out.println("Hey");
        return "Hey";
    }

    @PostMapping(value = "signup")
    public ResponseEntity<Map<String , String>> signUpUser(@RequestBody Admin admin) {
        System.out.println(admin.getId());
        System.out.println(admin.getFirstName());
        System.out.println(admin.getLastName());
        System.out.println(admin.getEmail());
        System.out.println(admin.getPhoneNumber());
        System.out.println("SignUp Received");
        if((adminService.searchAdminByEmail(admin.getEmail())!=null)) {
            return new ResponseEntity<>(
                    Map.of("message","Admin "  + admin.getFirstName() + " already exists"),
                    HttpStatus.BAD_REQUEST
            );
        }
        adminService.createAdmin(admin);
        System.out.println("User created");
        return new ResponseEntity<>(
                Map.of("message","Admin " + admin.getFirstName() + " created successfully"),
                HttpStatus.CREATED
        );
    }

    @PostMapping(value = "login")
    public ResponseEntity<Map<String,String>> logInUser(@RequestBody Admin admin) {
        System.out.println("Login entered");
        System.out.println(admin.getEmail());
        System.out.println(admin.getPhoneNumber());
        if(adminService.searchAdminByEmail(admin.getEmail())!=null) {
            return new ResponseEntity<>(
                    Map.of("message", admin.getEmail()+" Successfully logged in"),
                    HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
                Map.of("message", admin.getEmail() + " not present"),
                HttpStatus.NOT_FOUND);
    }
}
