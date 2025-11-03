package com.example.mininetflix.repo;

import com.example.mininetflix.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for interacting with {@link User} entities. Provides a
 * convenience method to find a user by their email address which is
 * used as the username for authentication.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}