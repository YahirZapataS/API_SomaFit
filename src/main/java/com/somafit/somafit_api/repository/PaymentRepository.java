package com.somafit.somafit_api.repository;

import com.somafit.somafit_api.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository <Payment, Long> {
    List<Payment> findByUserId(Long userId);
}
