package com.healthcare.service;

import com.healthcare.entity.Role;
import com.healthcare.entity.User;
import com.healthcare.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminSetupService {

    private final UserRepository userRepository;

    public AdminSetupService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String createAdminOnce() {

        if (userRepository.existsByRole(Role.ADMIN)) {
            return "ADMIN already exists";
        }

        User admin = new User();
        admin.setFullName("Admin");
        admin.setMobileNumber("9999999999");
        admin.setPassword("admin123"); // plain password as requested
        admin.setRole(Role.ADMIN);

        userRepository.save(admin);

        return "ADMIN created successfully";
    }
}
