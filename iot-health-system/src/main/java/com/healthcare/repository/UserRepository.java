package com.healthcare.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.healthcare.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByMobileNumber(String mobile);

    @Query("SELECT u FROM User u WHERE u.role <> 'ADMIN'")
    List<User> findAllPatients();
}
