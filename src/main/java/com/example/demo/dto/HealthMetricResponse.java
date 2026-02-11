package com.example.demo.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Data
public class HealthMetricResponse {
    private Long id;
    private String userId;
    private Integer steps;
    private BigDecimal distanceMeters;
    private BigDecimal caloriesKcal;
    private LocalDate logDate;
    private OffsetDateTime createdAt;
}
