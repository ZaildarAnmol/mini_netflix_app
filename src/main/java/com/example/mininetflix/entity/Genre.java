package com.example.mininetflix.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A genre represents a categorisation of movies such as Drama, Action or
 * Sci‑Fi. Genres are unique by name and referenced from Movie via a
 * many‑to‑many association.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the genre. Must be unique and non‑null.
     */
    @Column(unique = true, nullable = false)
    private String name;
}