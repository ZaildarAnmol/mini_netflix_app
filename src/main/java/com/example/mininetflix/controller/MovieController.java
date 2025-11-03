package com.example.mininetflix.controller;

import com.example.mininetflix.entity.Movie;
import com.example.mininetflix.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * MVC controller responsible for rendering pages related to movies. Handles
 * listing, searching and displaying individual movie details.
 */
@Controller
public class MovieController {

    @Autowired
    private MovieService movieService;

    /**
     * Render the home page showing a paginated list of all movies.
     *
     * @param page  zero‑based page index (default 0)
     * @param model model used by Thymeleaf to render the view
     * @return the name of the Thymeleaf template to render
     */
    @GetMapping("/")
    public String home(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Movie> movies = movieService.listAll(page, 10);
        model.addAttribute("movies", movies);
        return "index";
    }

    /**
     * Render the search results page. Delegates to the service to perform a
     * full‑text search across title and description.
     *
     * @param q     the query string
     * @param model model used by Thymeleaf
     * @return the index view with filtered movies
     */
    @GetMapping("/search")
    public String search(@RequestParam String q, Model model) {
        Page<Movie> results = movieService.search(q, 0, 10);
        model.addAttribute("movies", results);
        model.addAttribute("query", q);
        return "index";
    }

    /**
     * Show the details of a single movie.
     *
     * @param id    movie identifier
     * @param model model for the view
     * @return the detail view name
     */
    @GetMapping("/movie/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Movie movie = movieService.get(id);
        model.addAttribute("movie", movie);
        return "movie-detail";
    }
}