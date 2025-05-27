package com.somafit.somafit_api.repository;

import com.somafit.somafit_api.dto.SubscriptionReportDTO;
import com.somafit.somafit_api.model.Subscription;
import com.somafit.somafit_api.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findByUserId(Long userId);

    @Query("SELECT new com.somafit.somafit_api.dto.SubscriptionReportDTO(s.type, COUNT(s)) " +
            "FROM Subscription s " +
            "WHERE s.startDate BETWEEN :start AND :end " +
            "GROUP BY s.type")
    List<SubscriptionReportDTO> getSubscriptionReport(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end);

}
