package com.somafit.somafit_api.controller;

import com.somafit.somafit_api.model.*;
import com.somafit.somafit_api.repository.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/access")
public class AccessController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private SubscriptionRepository subscriptionRepo;

    @Autowired
    private AccessLogRepository accessLogRepo;

    @PostMapping("/verify")
    public AccessResponse verifyAccess(@RequestBody AccessRequest request) {
        User user = userRepo.findById(request.getUserId()).orElse(null);
        boolean accessGranted = false;
        String message = "Acceso denegado";

        if (user != null) {
            List<Subscription> subs = subscriptionRepo.findByUserId(user.getId());
            boolean hasActive = subs.stream().anyMatch(sub ->
                sub.getStartDate() != null &&
                sub.getEndDate() != null &&
                !LocalDate.now().isBefore(sub.getStartDate()) &&
                !LocalDate.now().isAfter(sub.getEndDate())
            );
            accessGranted = hasActive;
            message = hasActive ? "Acceso permitido" : "Suscripción vencida";
        }

        AccessLog log = new AccessLog();
        log.setUser(user);
        log.setAccessTime(LocalDateTime.now());
        log.setAccessGranted(accessGranted);
        accessLogRepo.save(log);

        return new AccessResponse(accessGranted, message, log.getAccessTime());
    }

    @GetMapping("/logs")
    public List<AccessLog> getLogs() {
        return accessLogRepo.findAll();
    }

    @Data
    public static class AccessRequest {
        private Long userId;
    }

    @Data
    @AllArgsConstructor
    public static class AccessResponse {
        private Boolean access;
        private String message;
        private LocalDateTime timestamp;
    }
}