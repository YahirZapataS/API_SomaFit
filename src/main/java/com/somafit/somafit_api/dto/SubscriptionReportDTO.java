package com.somafit.somafit_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubscriptionReportDTO {
    private String type;
    private Long count;
}