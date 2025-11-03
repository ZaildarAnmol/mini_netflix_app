package com.example.mininetflix.repo;

import com.example.mininetflix.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository exposing CRUD operations for {@link Genre} entities.
 */
public interface GenreRepository extends JpaRepository<Genre, Long> {
}