package com.somafit.somafit_api.repository;

import com.somafit.somafit_api.dto.IncomeReportDTO;
import com.somafit.somafit_api.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByUserId(Long userId);

    @Query("SELECT new com.somafit.somafit_api.dto.IncomeReportDTO(p.method, SUM(p.amount)) " +
            "FROM Payment p WHERE p.date BETWEEN :start AND :end GROUP BY p.method")
    List<IncomeReportDTO> getIncomeReport(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT new com.somafit.somafit_api.dto.IncomeReportDTO(p.method, SUM(p.amount)) " +
            "FROM Payment p " +
            "WHERE p.date BETWEEN :start AND :end " +
            "AND (:method IS NULL OR p.method = :method) " +
            "AND (:userId IS NULL OR p.user.id = :userId) " +
            "GROUP BY p.method")
    List<IncomeReportDTO> getIncomeReportWithFilters(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end,
            @Param("method") String method,
            @Param("userId") Long userId);

}