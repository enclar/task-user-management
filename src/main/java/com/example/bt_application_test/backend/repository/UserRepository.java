package com.example.bt_application_test.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bt_application_test.backend.data.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmailAddress(String emailAddress);
}
