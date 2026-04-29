package com.klef.fsad.sdp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.klef.fsad.sdp.dto.AuthRequestDTO;
import com.klef.fsad.sdp.entity.Admin;
import com.klef.fsad.sdp.entity.User;
import com.klef.fsad.sdp.security.JwtUtil;
import com.klef.fsad.sdp.service.AdminService;
import com.klef.fsad.sdp.service.UserService;

@RestController
@RequestMapping("auth")
@CrossOrigin("*")
public class AuthController 
{
    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    // USER LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> userlogin(
            @RequestBody AuthRequestDTO request)
    {
        try
        {
            User user = userService.verifyUserLogin(
                    request.getLogin(),
                    request.getPassword()
            );

            if(user != null)
            {
                String token =
                        jwtUtil.generateToken(
                                user.getUsername());

                return ResponseEntity.ok(
                        Map.of(
                                "token", token,
                                "role", "USER",
                                "username",
                                user.getUsername()
                        )
                );
            }
            else
            {
                return ResponseEntity
                        .status(401)
                        .body("Invalid User Credentials");
            }
        }
        catch(Exception e)
        {
            return ResponseEntity
                    .status(500)
                    .body("Internal Server Error");
        }
    }

    // ADMIN LOGIN
    @PostMapping("/admin-login")
    public ResponseEntity<?> adminLogin(
            @RequestBody AuthRequestDTO request)
    {
        try
        {
            Admin admin =
                    adminService.verifyAdminLogin(
                            request.getLogin(),
                            request.getPassword()
                    );

            if(admin != null)
            {
                String token =
                        jwtUtil.generateToken(
                                admin.getUsername()
                        );

                return ResponseEntity.ok(
                        Map.of(
                                "token", token,
                                "role", "ADMIN",
                                "username",
                                admin.getUsername()
                        )
                );
            }
            else
            {
                return ResponseEntity
                        .status(401)
                        .body("Invalid Admin Credentials");
            }
        }
        catch(Exception e)
        {
            return ResponseEntity
                    .status(500)
                    .body("Internal Server Error");
        }
    }
}