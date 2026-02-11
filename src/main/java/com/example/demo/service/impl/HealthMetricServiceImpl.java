package com.example.demo.service.impl;

import com.example.demo.dto.HealthMetricResponse;
import com.example.demo.dto.HealthMetricSaveRequest;
import com.example.demo.entity.UserHealthMetrics;
import com.example.demo.repository.UserHealthMetricsRepository;
import com.example.demo.service.HealthMetricService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HealthMetricServiceImpl implements HealthMetricService {

    private final UserHealthMetricsRepository repository;

    @Override
    @Transactional
    public HealthMetricResponse saveOrUpdate(HealthMetricSaveRequest request) {
        UserHealthMetrics entity = repository.findByUserIdAndLogDate(request.getUserId(), request.getLogDate())
                .orElse(new UserHealthMetrics());

        entity.setUserId(request.getUserId());
        entity.setLogDate(request.getLogDate());
        
        if (request.getSteps() != null) {
            entity.setSteps(request.getSteps());
        }
        if (request.getDistanceMeters() != null) {
            entity.setDistanceMeters(request.getDistanceMeters());
        }
        if (request.getCaloriesKcal() != null) {
            entity.setCaloriesKcal(request.getCaloriesKcal());
        }

        UserHealthMetrics saved = repository.save(entity);
        return mapToResponse(saved);
    }

    @Override
    public Optional<HealthMetricResponse> getByUserIdAndDate(String userId, LocalDate date) {
        return repository.findByUserIdAndLogDate(userId, date)
                .map(this::mapToResponse);
    }

    private HealthMetricResponse mapToResponse(UserHealthMetrics entity) {
        HealthMetricResponse response = new HealthMetricResponse();
        response.setId(entity.getId());
        response.setUserId(entity.getUserId());
        response.setSteps(entity.getSteps());
        response.setDistanceMeters(entity.getDistanceMeters());
        response.setCaloriesKcal(entity.getCaloriesKcal());
        response.setLogDate(entity.getLogDate());
        response.setCreatedAt(entity.getCreatedAt());
        return response;
    }
}
