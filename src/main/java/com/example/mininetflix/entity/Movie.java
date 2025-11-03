package com.example.mininetflix.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a piece of video content. A movie record stores
 * metadata such as its title, description, release year, rating, runtime,
 * poster URL and whether it is a movie or a show. Movies may belong to
 * multiple genres and the association is maintained via a join table.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The display title of the movie. Cannot be null.
     */
    @Column(nullable = false)
    private String title;

    /**
     * A detailed synopsis. Stored as text to allow arbitrarily long descriptions.
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * The year the movie was released.
     */
    private Integer releaseYear;

    /**
     * An aggregate rating on a 10‑point scale.
     */
    private BigDecimal rating;

    /**
     * Runtime of the movie in minutes.
     */
    private Integer runtimeMinutes;

    /**
     * Relative path to an image stored in the static resources used as a poster.
     */
    private String posterUrl;

    /**
     * The type of content (e.g. MOVIE or SHOW).
     */
    private String contentType;

    /**
     * Genres associated with this movie. Lazy loading is used to avoid
     * unnecessary queries when genres aren't needed.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "movie_genres",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres = new HashSet<>();
}