package com.klef.fsad.sdp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klef.fsad.sdp.dto.AuthRequestDTO;
import com.klef.fsad.sdp.entity.Admin;
import com.klef.fsad.sdp.entity.User;
import com.klef.fsad.sdp.service.AdminService;
import com.klef.fsad.sdp.service.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    // USER LOGIN (NO JWT)
    @PostMapping("/login")
    public ResponseEntity<?> userlogin(@RequestBody AuthRequestDTO request) {
        try {
            User user = userService.verifyUserLogin(
                    request.getLogin(),
                    request.getPassword()
            );

            if (user != null) {
                return ResponseEntity.ok(
                        Map.of(
                                "message", "Login Success",
                                "username", user.getUsername(),
                                "role", "USER"
                        )
                );
            } else {
                return ResponseEntity.status(401)
                        .body("Invalid User Credentials");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("Internal Server Error");
        }
    }

    // ADMIN LOGIN (NO JWT)
    @PostMapping("/admin-login")
    public ResponseEntity<?> adminLogin(@RequestBody AuthRequestDTO request) {
        try {
            Admin admin = adminService.verifyAdminLogin(
                    request.getLogin(),
                    request.getPassword()
            );

            if (admin != null) {
                return ResponseEntity.ok(
                        Map.of(
                                "message", "Admin Login Success",
                                "username", admin.getUsername(),
                                "role", "ADMIN"
                        )
                );
            } else {
                return ResponseEntity.status(401)
                        .body("Invalid Admin Credentials");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("Internal Server Error");
        }
    }
}
