package com.example.bt_application_test.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
import com.example.bt_application_test.backend.data.User;
import com.example.bt_application_test.backend.service.UserService;

@RestController
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/users")
    public @ResponseBody Iterable<User> findAllUsers() {
        return userService.findAll();
    }

    public @ResponseBody Optional<User> findUserById(Integer id) {
        log.info("ID?", id);
        return userService.findUserById(id);
    }

    public void deleteUser(Integer id) {
        userService.delete(id);
    }

    public void createUser(@RequestBody User user) {
        userService.createOrUpdate(user);
    }

    // @PutMapping("/users")
    // public void updateUser(@RequestBody User user) {
    //     userService.createOrUpdate(user);
    // }
}
