package com.somafit.somafit_api.service;

import com.somafit.somafit_api.model.User;
import com.somafit.somafit_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    public User register(User u) {
        u.setPassword(encoder.encode(u.getPassword()));
        return repo.save(u);
    }

    public User findByUsername(String username) {
        return repo.findByUsername(username).orElse(null);
    }
}
