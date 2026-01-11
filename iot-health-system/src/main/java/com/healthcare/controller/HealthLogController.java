package com.healthcare.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.healthcare.response.ApiResponse;
import com.healthcare.service.HealthLogService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")

public class HealthLogController {

    private final HealthLogService healthLogService;

    public HealthLogController(HealthLogService healthLogService) {
		super();
		this.healthLogService = healthLogService;
	}

	@GetMapping("/logs/me/{userId}")
    public ApiResponse<?> myLogs(@PathVariable Long userId) {

        return new ApiResponse<>(
                LocalDateTime.now(),
                "SUCCESS",
                200,
                "My health logs",
                healthLogService.getMyLogs(userId)
        );
    }

    @GetMapping("/admin/logs/{adminId}/{userId}")
    public ApiResponse<?> patientLogs(
            @PathVariable Long userId,
            @PathVariable Long adminId) {

        return new ApiResponse<>(
                LocalDateTime.now(),
                "SUCCESS",
                200,
                "Patient health logs",
                healthLogService.getPatientLogs(adminId, userId)
        );
    }
}
