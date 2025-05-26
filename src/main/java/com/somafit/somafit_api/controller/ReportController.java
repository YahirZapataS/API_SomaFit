package com.somafit.somafit_api.controller;

import com.somafit.somafit_api.dto.AccessReportDTO;
import com.somafit.somafit_api.dto.IncomeReportDTO;
import com.somafit.somafit_api.dto.SubscriptionReportDTO;
import com.somafit.somafit_api.repository.AccessLogRepository;
import com.somafit.somafit_api.repository.PaymentRepository;
import com.somafit.somafit_api.repository.SubscriptionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private PaymentRepository paymentRepo;

    @Autowired
    private SubscriptionRepository subscriptionRepo;

    @Autowired
    private AccessLogRepository accessLogRepo;

    @GetMapping("/income")
    public List<IncomeReportDTO> getIncomeReport(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam(required = false) String method,
            @RequestParam(required = false) Long userId) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return paymentRepo.getIncomeReportWithFilters(startDate, endDate, method, userId);
    }

    @GetMapping("/subscriptions")
    public List<SubscriptionReportDTO> getSubscriptionReport(
            @RequestParam String start,
            @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return subscriptionRepo.getSubscriptionReport(startDate, endDate);
    }

    @GetMapping("/accesses")
    public List<AccessReportDTO> getAccessReport(
            @RequestParam String start,
            @RequestParam String end) {
        LocalDateTime startDate = LocalDateTime.parse(start + "T00:00:00");
        LocalDateTime endDate = LocalDateTime.parse(end + "T23:59:59");
        return accessLogRepo.getAccessReport(startDate, endDate);
    }

}