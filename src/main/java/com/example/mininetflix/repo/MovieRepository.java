package com.example.mininetflix.repo;

import com.example.mininetflix.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository for performing CRUD operations on {@link Movie} entities.
 * The custom {@code search} method allows filtering movies by matching
 * a search term against the title or description.
 */
public interface MovieRepository extends JpaRepository<Movie, Long> {

    /**
     * Search for movies where the title or description contains the supplied query.
     * The search is case insensitive and uses SQL wildcards on both sides of the
     * query string. Results are returned as a paginated list ordered by rating
     * descending (the ordering is specified by the caller).
     *
     * @param q       search term
     * @param pageable pagination information including page number, size and sort
     * @return a page of matching movies
     */
    @Query("""
            SELECT m FROM Movie m
            WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :q, '%'))
               OR LOWER(m.description) LIKE LOWER(CONCAT('%', :q, '%'))
            """)
    Page<Movie> search(@Param("q") String q, Pageable pageable);
}