package com.somafit.somafit_api.controller;

import com.somafit.somafit_api.model.User;
import com.somafit.somafit_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User updateUser) {
        return userRepository.findById(id).map(user -> {
            user.setName(updateUser.getName());
            user.setUsername(updateUser.getUsername());
            user.setPassword(updateUser.getPassword());
            user.setRole(updateUser.getRole());
            return userRepository.save(user);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}
