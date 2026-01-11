package com.healthcare.controller;

import com.healthcare.dto.LoginRequestDto;
import com.healthcare.dto.RegisterRequestDto;
import com.healthcare.entity.User;
import com.healthcare.response.ApiResponse;
import com.healthcare.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody RegisterRequestDto dto) {

        userService.register(dto);

        ApiResponse<Void> response = new ApiResponse<>(
                LocalDateTime.now(),
                "SUCCESS",
                201,
                "Patient registered successfully",
                null
        );

        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<User>> login(@RequestBody LoginRequestDto dto) {

        User user = userService.login(dto);

        ApiResponse<User> response = new ApiResponse<>(
                LocalDateTime.now(),
                "SUCCESS",
                200,
                "Login successful",
                user
        );

        return ResponseEntity.ok(response);
    }
}