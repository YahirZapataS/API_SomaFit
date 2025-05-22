package com.somafit.somafit_api.controller;

import com.somafit.somafit_api.config.JwtUtil;
import com.somafit.somafit_api.model.User;
import com.somafit.somafit_api.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private UserService service;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return service.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request) {
        User u = service.findByUsername(request.getUsername());
        if (u != null && request.getPassword().equals(u.getPassword())) {
            return jwtUtil.generateToken(u.getUsername());
        }

        return "Invalid Credentials";
    }

    @Data
    public static class AuthRequest {
        private String username;
        private String password;
    }
}
