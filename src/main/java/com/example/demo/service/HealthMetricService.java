package com.example.demo.service;

import com.example.demo.dto.HealthMetricResponse;
import com.example.demo.dto.HealthMetricSaveRequest;

import java.time.LocalDate;
import java.util.Optional;

public interface HealthMetricService {
    HealthMetricResponse saveOrUpdate(HealthMetricSaveRequest request);
    Optional<HealthMetricResponse> getByUserIdAndDate(String userId, LocalDate date);
}
