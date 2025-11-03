package com.example.mininetflix.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Application user. Stores credentials (email, password hash) and role
 * information. Users may mark movies as favourites via the Favourite
 * entity. The {@code enabled} flag allows disabling accounts without
 * deleting them.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Email used as a unique identifier for login.
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * BCrypt hashed password. Never store raw passwords.
     */
    @Column(nullable = false)
    private String passwordHash;

    /**
     * Display name shown in the UI.
     */
    private String displayName;

    /**
     * Role of the user. Prefixed with ROLE_ when converted to a Spring authority.
     */
    @Column(nullable = false)
    private String role = "USER";

    /**
     * If false, the account is considered disabled and cannot authenticate.
     */
    @Column(nullable = false)
    private boolean enabled = true;
}