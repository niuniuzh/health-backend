package com.example.demo.controller;

import com.example.demo.dto.HealthMetricResponse;
import com.example.demo.dto.HealthMetricSaveRequest;
import com.example.demo.service.HealthMetricService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/health-metrics")
@RequiredArgsConstructor
public class HealthMetricController {

    private final HealthMetricService healthMetricService;

    @PostMapping("/save")
    public ResponseEntity<HealthMetricResponse> savedata(@Valid @RequestBody HealthMetricSaveRequest request) {
        HealthMetricResponse response = healthMetricService.saveOrUpdate(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<HealthMetricResponse> getdata(
            @RequestParam String userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate logDate) {
        
        return healthMetricService.getByUserIdAndDate(userId, logDate)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
