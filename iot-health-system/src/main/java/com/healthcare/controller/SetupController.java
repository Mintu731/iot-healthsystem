package com.healthcare.controller;

import com.healthcare.service.AdminSetupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/setup")
public class SetupController {

    private final AdminSetupService adminSetupService;

    public SetupController(AdminSetupService adminSetupService) {
        this.adminSetupService = adminSetupService;
    }

    @PostMapping("/create-admin")
    public ResponseEntity<String> createAdmin() {
        return ResponseEntity.ok(adminSetupService.overwriteAdmin());
    }
}
