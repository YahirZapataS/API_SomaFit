package com.somafit.somafit_api.controller;

import com.somafit.somafit_api.model.*;
import com.somafit.somafit_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private SubscriptionRepository subscriptionRepo;

    @PostMapping("/register")
    public Payment register(@RequestParam Long userId,
            @RequestParam Long subscriptionId,
            @RequestBody Payment payment) {

        User user = userRepo.findById(userId).orElse(null);
        Subscription subscription = subscriptionRepo.findById(subscriptionId).orElse(null);

        if (user != null && subscription != null) {
            payment.setUser(user);
            payment.setSubscription(subscription);
            payment.setDate(LocalDate.now());
            return paymentRepo.save(payment);
        }

        return null;
    }

    @GetMapping
    public List<Payment> getAll() {
        return paymentRepo.findAll();
    }

    @GetMapping("/user/{userId}")
    public List<Payment> getByUser(@PathVariable Long userId) {
        return paymentRepo.findByUserId(userId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        paymentRepo.deleteById(id);
    }
}
