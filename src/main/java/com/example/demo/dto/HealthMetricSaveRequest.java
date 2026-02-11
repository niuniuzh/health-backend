package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class HealthMetricSaveRequest {

    @NotBlank(message = "User ID cannot be blank")
    private String userId;

    private Integer steps;

    private BigDecimal distanceMeters;

    private BigDecimal caloriesKcal;

    @NotNull(message = "Log date cannot be null")
    private LocalDate logDate;
}
