package com.cinema.cineshow.controller;

import com.cinema.cineshow.model.Role;
import com.cinema.cineshow.model.User;
import com.cinema.cineshow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder bcryptPasswordEncoder;

    @PostMapping("")
    public User createUser(@RequestBody User user) throws Exception {
        user.setPassword(this.bcryptPasswordEncoder.encode(user.getPassword()));

        Role role = new Role();
        role.setRoleName("NORMAL");
        role.setRoleId(66L);
//        user.setRole(role);
        this.userService.createUser(user, role);
        return user;
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable("username") String username) {
        return this.userService.getUser(username);
    }

    @PutMapping("")
    public User updateUser(@RequestBody User user) {
        return this.userService.updateUser(user);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        this.userService.deleteUser(userId);
    }
}
