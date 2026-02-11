package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "user_health_metrics", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "log_date"})
})
@Data
public class UserHealthMetrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, length = 50)
    private String userId;

    @Column(name = "steps")
    private Integer steps = 0;

    @Column(name = "distance_meters", precision = 10, scale = 2)
    private BigDecimal distanceMeters = BigDecimal.ZERO;

    @Column(name = "calories_kcal", precision = 10, scale = 2)
    private BigDecimal caloriesKcal = BigDecimal.ZERO;

    @Column(name = "log_date", nullable = false)
    private LocalDate logDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
    }
}
