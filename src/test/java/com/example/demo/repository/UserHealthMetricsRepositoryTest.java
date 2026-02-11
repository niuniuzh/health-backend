package com.example.demo.repository;

import com.example.demo.entity.UserHealthMetrics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserHealthMetricsRepositoryTest {

    @Autowired
    private UserHealthMetricsRepository repository;

    @Test
    void findByUserIdAndLogDate_RecordExists_ShouldReturnOptional() {
        UserHealthMetrics entity = new UserHealthMetrics();
        entity.setUserId("user1");
        entity.setLogDate(LocalDate.of(2023, 10, 27));
        repository.save(entity);

        Optional<UserHealthMetrics> found = repository.findByUserIdAndLogDate("user1", LocalDate.of(2023, 10, 27));

        assertTrue(found.isPresent());
        assertEquals("user1", found.get().getUserId());
    }

    @Test
    void findByUserIdAndLogDate_RecordDoesNotExist_ShouldReturnEmpty() {
        Optional<UserHealthMetrics> found = repository.findByUserIdAndLogDate("nonexistent", LocalDate.now());

        assertFalse(found.isPresent());
    }
}
