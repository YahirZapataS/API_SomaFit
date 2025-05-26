package com.somafit.somafit_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccessReportDTO {
    private Boolean accessGranted;
    private Long count;
}