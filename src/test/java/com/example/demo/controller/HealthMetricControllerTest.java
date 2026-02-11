package com.example.demo.controller;

import com.example.demo.dto.HealthMetricResponse;
import com.example.demo.dto.HealthMetricSaveRequest;
import com.example.demo.service.HealthMetricService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HealthMetricController.class)
class HealthMetricControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HealthMetricService healthMetricService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void savedata_ValidRequest_ShouldReturnOk() throws Exception {
        HealthMetricSaveRequest request = new HealthMetricSaveRequest();
        request.setUserId("user1");
        request.setLogDate(LocalDate.now());
        request.setSteps(1000);

        HealthMetricResponse response = new HealthMetricResponse();
        response.setUserId("user1");
        response.setSteps(1000);

        when(healthMetricService.saveOrUpdate(any(HealthMetricSaveRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/health-metrics/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("user1"))
                .andExpect(jsonPath("$.steps").value(1000));
    }

    @Test
    void savedata_InvalidRequest_ShouldReturnBadRequest() throws Exception {
        HealthMetricSaveRequest request = new HealthMetricSaveRequest(); // Missing required fields

        mockMvc.perform(post("/api/health-metrics/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getdata_RecordExists_ShouldReturnOk() throws Exception {
        HealthMetricResponse response = new HealthMetricResponse();
        response.setUserId("user1");
        response.setLogDate(LocalDate.now());

        when(healthMetricService.getByUserIdAndDate(anyString(), any(LocalDate.class))).thenReturn(Optional.of(response));

        mockMvc.perform(get("/api/health-metrics")
                        .param("userId", "user1")
                        .param("logDate", LocalDate.now().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("user1"));
    }

    @Test
    void getdata_RecordNotFound_ShouldReturnNotFound() throws Exception {
        when(healthMetricService.getByUserIdAndDate(anyString(), any(LocalDate.class))).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/health-metrics")
                        .param("userId", "user1")
                        .param("logDate", LocalDate.now().toString()))
                .andExpect(status().isNotFound());
    }
}
