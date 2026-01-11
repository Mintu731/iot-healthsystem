package com.healthcare.exception;

import com.healthcare.response.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ✅ 409 - Duplicate mobile number
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateMobile(
            DataIntegrityViolationException ex) {

        ApiResponse<Void> response = new ApiResponse<>(
                LocalDateTime.now(),
                "FAIL",
                409,
                "Mobile number already exists",
                null
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    // ✅ 400 - Bad request (invalid input, role mismatch, etc.)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadRequest(
            IllegalArgumentException ex) {

        ApiResponse<Void> response = new ApiResponse<>(
                LocalDateTime.now(),
                "FAIL",
                400,
                ex.getMessage(),
                null
        );

        return ResponseEntity.badRequest().body(response);
    }

    // ✅ 403 - Forbidden (business rules like admin delete)
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiResponse<Void>> handleForbidden(
            IllegalStateException ex) {

        ApiResponse<Void> response = new ApiResponse<>(
                LocalDateTime.now(),
                "FAIL",
                403,
                ex.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }
}
