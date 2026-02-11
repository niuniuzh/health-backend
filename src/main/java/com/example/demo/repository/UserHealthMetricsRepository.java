package com.example.demo.repository;

import com.example.demo.entity.UserHealthMetrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface UserHealthMetricsRepository extends JpaRepository<UserHealthMetrics, Long> {
    Optional<UserHealthMetrics> findByUserIdAndLogDate(String userId, LocalDate logDate);
}
