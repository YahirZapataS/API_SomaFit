package com.somafit.somafit_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IncomeReportDTO {
    private String method;
    private Double total;
}