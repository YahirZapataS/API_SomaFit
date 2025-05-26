package com.somafit.somafit_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.somafit.somafit_api.config.JwtUtil;
import com.somafit.somafit_api.model.User;
import com.somafit.somafit_api.service.UserService;

import lombok.Data;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService service;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return service.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request) {
        User u = service.findByUsername(request.getUsername());
        if (u != null && encoder.matches(request.getPassword(), u.getPassword())) {
            return jwtUtil.generateToken(u.getUsername(), u.getRole());
        }
        return "Credenciales inválidas";
    }

    @Data
    public static class AuthRequest {
        private String username;
        private String password;
    }
}