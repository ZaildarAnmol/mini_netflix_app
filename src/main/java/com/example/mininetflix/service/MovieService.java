package com.example.mininetflix.service;

import com.example.mininetflix.entity.Movie;
import com.example.mininetflix.repo.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Business logic related to movies. Encapsulates calls to the underlying
 * repository and exposes methods suitable for use by controllers.
 */
@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    /**
     * Search for movies matching the given query. Results are paginated and
     * ordered by rating in descending order so that higher rated movies appear
     * first.
     *
     * @param q     search term
     * @param page  zero‑based page index
     * @param size  page size
     * @return a page of movies matching the query
     */
    public Page<Movie> search(String q, int page, int size) {
        return repository.search(q, PageRequest.of(page, size, Sort.by("rating").descending()));
    }

    /**
     * List all movies paginated in natural order.
     *
     * @param page zero‑based page index
     * @param size page size
     * @return a page of all movies
     */
    public Page<Movie> listAll(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    /**
     * Retrieve a single movie by its id.
     *
     * @param id primary key of the movie
     * @return the movie or {@code null} if not found
     */
    public Movie get(Long id) {
        return repository.findById(id).orElse(null);
    }
}