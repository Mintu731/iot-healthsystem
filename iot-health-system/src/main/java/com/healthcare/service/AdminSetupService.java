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

    public String overwriteAdmin() {

        // Fetch existing admin or create new one
        User admin = userRepository.findByRole(Role.ADMIN)
                .orElse(new User());

        // Always overwrite details
        admin.setFullName("Admin");
        admin.setMobileNumber("9515353724");
        admin.setPassword("1234"); // plain password as requested
        admin.setRole(Role.ADMIN);

        userRepository.save(admin);

        return "ADMIN details overwritten successfully";
    }
}
