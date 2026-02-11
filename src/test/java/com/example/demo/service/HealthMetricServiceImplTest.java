package com.example.demo.service;

import com.example.demo.dto.HealthMetricResponse;
import com.example.demo.dto.HealthMetricSaveRequest;
import com.example.demo.entity.UserHealthMetrics;
import com.example.demo.repository.UserHealthMetricsRepository;
import com.example.demo.service.impl.HealthMetricServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HealthMetricServiceImplTest {

    @Mock
    private UserHealthMetricsRepository repository;

    @InjectMocks
    private HealthMetricServiceImpl healthMetricService;

    private HealthMetricSaveRequest saveRequest;
    private UserHealthMetrics entity;

    @BeforeEach
    void setUp() {
        saveRequest = new HealthMetricSaveRequest();
        saveRequest.setUserId("user1");
        saveRequest.setLogDate(LocalDate.now());
        saveRequest.setSteps(1000);
        saveRequest.setDistanceMeters(new BigDecimal("800.0"));
        saveRequest.setCaloriesKcal(new BigDecimal("50.0"));

        entity = new UserHealthMetrics();
        entity.setId(1L);
        entity.setUserId("user1");
        entity.setLogDate(LocalDate.now());
    }

    @Test
    void saveOrUpdate_NewRecord_ShouldCreate() {
        when(repository.findByUserIdAndLogDate(anyString(), any(LocalDate.class))).thenReturn(Optional.empty());
        when(repository.save(any(UserHealthMetrics.class))).thenAnswer(invocation -> invocation.getArgument(0));

        HealthMetricResponse response = healthMetricService.saveOrUpdate(saveRequest);

        assertNotNull(response);
        assertEquals(saveRequest.getUserId(), response.getUserId());
        assertEquals(saveRequest.getSteps(), response.getSteps());
        verify(repository).save(any(UserHealthMetrics.class));
    }

    @Test
    void saveOrUpdate_ExistingRecord_ShouldUpdate() {
        when(repository.findByUserIdAndLogDate(anyString(), any(LocalDate.class))).thenReturn(Optional.of(entity));
        when(repository.save(any(UserHealthMetrics.class))).thenReturn(entity);

        HealthMetricResponse response = healthMetricService.saveOrUpdate(saveRequest);

        assertNotNull(response);
        assertEquals(saveRequest.getSteps(), response.getSteps());
        verify(repository).save(entity);
    }

    @Test
    void getByUserIdAndDate_RecordExists_ShouldReturnResponse() {
        when(repository.findByUserIdAndLogDate("user1", LocalDate.now())).thenReturn(Optional.of(entity));

        Optional<HealthMetricResponse> response = healthMetricService.getByUserIdAndDate("user1", LocalDate.now());

        assertTrue(response.isPresent());
        assertEquals("user1", response.get().getUserId());
    }

    @Test
    void getByUserIdAndDate_RecordDoesNotExist_ShouldReturnEmpty() {
        when(repository.findByUserIdAndLogDate("user1", LocalDate.now())).thenReturn(Optional.empty());

        Optional<HealthMetricResponse> response = healthMetricService.getByUserIdAndDate("user1", LocalDate.now());

        assertFalse(response.isPresent());
    }
}
