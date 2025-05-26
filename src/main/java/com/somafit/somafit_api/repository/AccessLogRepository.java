package com.somafit.somafit_api.repository;

import com.somafit.somafit_api.dto.AccessReportDTO;
import com.somafit.somafit_api.model.AccessLog;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {
    @Query("SELECT new com.somafit.somafit_api.dto.AccessReportDTO(a.accessGranted, COUNT(a)) " +
            "FROM AccessLog a " +
            "WHERE a.accessTime BETWEEN :start AND :end " +
            "GROUP BY a.accessGranted")
    List<AccessReportDTO> getAccessReport(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

}