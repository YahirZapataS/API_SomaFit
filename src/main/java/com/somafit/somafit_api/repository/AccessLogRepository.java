package com.somafit.somafit_api.repository;

import com.somafit.somafit_api.model.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {
}