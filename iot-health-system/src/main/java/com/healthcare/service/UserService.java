package com.healthcare.service;

import com.healthcare.dto.LoginRequestDto;
import com.healthcare.dto.RegisterRequestDto;
import com.healthcare.entity.Role;
import com.healthcare.entity.User;
import com.healthcare.repository.HealthLogRepository;
import com.healthcare.repository.UserRepository;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final HealthLogService healthLogService;
    private final HealthLogRepository healthLogRepository;

    // ✅ MANUAL CONSTRUCTOR (NO LOMBOK)
    public UserService(UserRepository userRepository,
                       HealthLogService healthLogService,
                       HealthLogRepository healthLogRepository) {
        this.userRepository = userRepository;
        this.healthLogService = healthLogService;
        this.healthLogRepository = healthLogRepository;
    }

    // ================= REGISTER =================
    public User register(RegisterRequestDto dto) {

        User user = new User();
        user.setMobileNumber(dto.getMobileNumber());
        user.setFullName(dto.getFullName());
        user.setPassword(dto.getPassword());
        user.setTsChannelId(dto.getTsChannelId());
        user.setTsReadKey(dto.getTsReadKey());
        user.setRole(Role.PATIENT);
        user.setLastEntryId(0L);

        // 1️⃣ Save user (THIS MUST ALWAYS SUCCEED)
        User savedUser = userRepository.save(user);

        healthLogService.syncFromThingSpeak(savedUser.getId());

        return savedUser;
    }
    // ================= LOGIN =================
    public User login(LoginRequestDto dto) {

        User user = userRepository.findByMobileNumber(dto.getMobileNumber())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!user.getPassword().equals(dto.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        if (!user.getRole().equals(dto.getRole())) {
            throw new IllegalArgumentException("Role mismatch");
        }

        return user;
    }

    // ================= GET PATIENTS =================
    public java.util.List<User> getAllPatients() {
        return userRepository.findAll()
                .stream()
                .filter(u -> u.getRole() != Role.ADMIN)
                .toList();
    }

    // ================= DELETE PATIENT =================
    @Transactional
    public void deletePatient(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.getRole() == Role.ADMIN) {
            throw new IllegalStateException("Admin user cannot be deleted");
        }

        // ✅ DELETE CHILD RECORDS FIRST
        healthLogRepository.deleteByUserId(id);

        // ✅ THEN DELETE USER
        userRepository.delete(user);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
