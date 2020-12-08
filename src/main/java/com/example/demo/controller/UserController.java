package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.model.User;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users/login")
    public ResponseEntity<User> login(@RequestParam String login, @RequestParam String password) {
        User user = userRepository.findByLoginAndPassword(login, password).orElseThrow(() -> new ResourceNotFoundException("User with such username and password doesn't exist :" + login));
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users/signup")
    public ResponseEntity<User> signup(@RequestParam String login, @RequestParam String password) {
        User user = userRepository.findByLoginAndPassword(login, password).orElseThrow(() -> new ResourceNotFoundException("User with such username and password doesn't exist :" + login));
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users/")
    public ResponseEntity<User> getUser(@RequestParam String login) {
        User user = userRepository.findByLogin(login).orElseThrow(() -> new ResourceNotFoundException("User with such username and password doesn't exist :" + login));
        return ResponseEntity.ok(user);
    }
}
