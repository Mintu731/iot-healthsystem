package com.healthcare.controller;

import com.healthcare.response.ApiResponse;
import com.healthcare.service.HealthLogService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
@CrossOrigin(
	    origins = {
	        "http://localhost:3000",
	        "https://iot-healthsystem.netlify.app"
	    }
	)
@RestController
@RequestMapping("/api/sync")
public class ThingSpeakSyncController {

    private final HealthLogService healthLogService;

    public ThingSpeakSyncController(HealthLogService healthLogService) {
        this.healthLogService = healthLogService;
    }

    @PostMapping("/patient/{userId}")
    public ApiResponse<Void> syncPatient(@PathVariable Long userId) {

        int count = healthLogService.syncFromThingSpeak(userId);

        return new ApiResponse<>(
                LocalDateTime.now(),   // ✅ FIXED
                "SUCCESS",
                200,
                "Successfully synced " + count + " new health records",
                null
        );
    }
}
