package com.somafit.somafit_api.controller;

import com.somafit.somafit_api.model.Subscription;
import com.somafit.somafit_api.model.User;
import com.somafit.somafit_api.repository.SubscriptionRepository;
import com.somafit.somafit_api.repository.UserRepository;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepo;

    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    @PostMapping("/register/{userId}")
    public Subscription register(@PathVariable Long userId, @RequestBody Subscription sub) {
        User user = userRepo.findById(userId).orElse(null);
        if (user != null) {
            sub.setUser(user);
            return subscriptionRepository.save(sub);
        }

        return null;
    }

    @GetMapping
    public List<Subscription> getAll() {
        return subscriptionRepository.findAll();
    }

    @GetMapping("/user/{userId}")
    public List<Subscription> getByUser(@PathVariable Long userId) {
        return subscriptionRepository.findByUserId(userId);
    }

    @PutMapping("/{id}")
    public Subscription update(@PathVariable Long id, @RequestBody Subscription updated) {
        return subscriptionRepository.findById(id).map(sub -> {
            sub.setType(updated.getType());
            sub.setStartDate(updated.getStartDate());
            sub.setEndDate(updated.getEndDate());
            sub.setAmount(updated.getAmount());
            return subscriptionRepository.save(sub);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        subscriptionRepository.deleteById(id);
    }

    @PostConstruct
    public void init() {
        System.out.println(">> SubscriptionController cargado correctamente");
    }
}
