package com.example.demo.controller;

import com.example.demo.dao.ProjectDao;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.model.Project;
import com.example.demo.model.User;
import com.example.demo.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProjectDao projectDao;

    @GetMapping("/users/login")
    public ResponseEntity<User> login(@RequestParam String login, @RequestParam String password) {
        User user = userDao.findByLoginAndPassword(login, password).orElseThrow(() -> new ResourceNotFoundException("User with such username and password doesn't exist :" + login));
        return ResponseEntity.ok(user);
    }

    @PutMapping("/users/signup")
    public ResponseEntity<User> signup(@RequestBody User user, @RequestParam String login, @RequestParam String password) {
        if (userDao.findByLogin(login).isPresent()) {
            throw new ResourceNotFoundException("User with such username already exist :" + login);
        } else {
            User newUser = userDao.save(user);
            return ResponseEntity.ok(newUser);
        }
    }

    @GetMapping("/users/")
    public ResponseEntity<User> getUser(@RequestParam String login) {
        User user = userDao.findByLogin(login).orElseThrow(() -> new ResourceNotFoundException("User with such username and password doesn't exist :" + login));
        return ResponseEntity.ok(user);
    }
}
