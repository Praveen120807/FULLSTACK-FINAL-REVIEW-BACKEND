package com.klef.fsad.sdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.klef.fsad.sdp.entity.User;
import com.klef.fsad.sdp.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String testUser() {
        return "User API Working 🔥";
    }

    @PostMapping("/signup")
    public String signup(@RequestBody User user) {
        return userService.userRegistration(user);
    }
}
