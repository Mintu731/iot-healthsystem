package com.healthcare.controller;

import com.healthcare.entity.User;
import com.healthcare.response.ApiResponse;
import com.healthcare.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(
    origins = {
        "http://localhost:3000",
        "https://iot-healthsystem.netlify.app"
    }
)
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/patients")
    public ResponseEntity<ApiResponse<List<User>>> getPatients() {

        List<User> patients = userService.getAllPatients();

        ApiResponse<List<User>> response = new ApiResponse<>(
                LocalDateTime.now(),
                "SUCCESS",
                200,
                "Patient records retrieved successfully. Admin record excluded.",
                patients
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/patients/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePatient(@PathVariable Long id) {

        userService.deletePatient(id);

        ApiResponse<Void> response = new ApiResponse<>(
                LocalDateTime.now(),
                "SUCCESS",
                200,
                "Patient deleted successfully",
                null
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {

        List<User> users = userService.getAllUsers();

        ApiResponse<List<User>> response = new ApiResponse<>(
                LocalDateTime.now(),
                "SUCCESS",
                200,
                "All user records retrieved (including ADMIN).",
                users
        );

        return ResponseEntity.ok(response);
    }
}
