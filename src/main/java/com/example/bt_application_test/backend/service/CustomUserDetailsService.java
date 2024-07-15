package com.example.bt_application_test.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.bt_application_test.backend.data.User;
import com.example.bt_application_test.backend.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
            .findByEmailAddress(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found:" + username));
        
        return org.springframework.security.core.userdetails.User
            .withUsername(user.getEmailAddress())
            .password(user.getPassword())
            .roles(user.getUserRoles().split(","))
            .build();
    }
}
