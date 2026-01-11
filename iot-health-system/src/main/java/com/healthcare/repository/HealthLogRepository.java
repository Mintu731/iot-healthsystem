package com.healthcare.repository;

import com.healthcare.entity.HealthLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface HealthLogRepository extends JpaRepository<HealthLog, Long> {

    // fetch logs
    java.util.List<HealthLog> findByUserId(Long userId);

    // ✅ delete logs for a user
    @Transactional
    void deleteByUserId(Long userId);
}
