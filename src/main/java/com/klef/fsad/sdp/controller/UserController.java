package com.klef.fsad.sdp.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {


@GetMapping
public String testUser() {
    return "User API Working 🔥";
}


}
