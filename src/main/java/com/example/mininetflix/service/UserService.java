package com.example.mininetflix.service;

import com.example.mininetflix.dto.RegisterRequest;
import com.example.mininetflix.entity.User;
import com.example.mininetflix.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handles user registration and encapsulates any user‑related business logic.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Register a new user. Validates that the email is not already registered and
     * that the password and confirmation match. Passwords are hashed using the
     * configured {@link PasswordEncoder}. On success the newly created user is
     * returned.
     *
     * @param request registration data from the client
     * @return the persisted user
     * @throws IllegalArgumentException if validation fails
     */
    @Transactional
    public User register(RegisterRequest request) {
        // check passwords match
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        // ensure email isn't already taken
        userRepository.findByEmail(request.getEmail()).ifPresent(u -> {
            throw new IllegalArgumentException("Email already in use");
        });
        // create and persist user
        User user = new User();
        user.setEmail(request.getEmail());
        user.setDisplayName(request.getDisplayName());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");
        user.setEnabled(true);
        return userRepository.save(user);
    }
}