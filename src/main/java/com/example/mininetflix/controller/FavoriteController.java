package com.example.mininetflix.controller;

import com.example.mininetflix.entity.Favorite;
import com.example.mininetflix.entity.Movie;
import com.example.mininetflix.repo.FavoriteRepository;
import com.example.mininetflix.repo.MovieRepository;
import com.example.mininetflix.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for managing favourite movies. Supports adding and removing
 * favourites as well as listing all favourites for the authenticated user.
 */
@Controller
@RequestMapping("/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Add a movie to the authenticated user's favourites. The movie is looked up
     * by id and persisted in a new {@link Favorite} entity.
     *
     * @param movieId   identifier of the movie to favourite
     * @param principal Spring Security principal representing the current user
     * @return redirect back to the movie detail page
     */
    @PostMapping("/{movieId}")
    public String addFavorite(@PathVariable Long movieId, @AuthenticationPrincipal UserDetails principal) {
        // look up the currently authenticated user
        var user = userRepository.findByEmail(principal.getUsername()).orElseThrow();
        var movie = movieRepository.findById(movieId).orElseThrow();
        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setMovie(movie);
        favoriteRepository.save(favorite);
        return "redirect:/movie/" + movieId;
    }

    /**
     * Remove a favourite for the authenticated user. Finds the favourite entry
     * matching both the user and movie and deletes it.
     *
     * @param movieId   identifier of the movie to unfavourite
     * @param principal authenticated user principal
     * @return redirect back to the movie detail page
     */
    @PostMapping("/{movieId}/remove")
    public String removeFavorite(@PathVariable Long movieId, @AuthenticationPrincipal UserDetails principal) {
        var user = userRepository.findByEmail(principal.getUsername()).orElseThrow();
        favoriteRepository.findAll().stream()
                .filter(f -> f.getUser().getId().equals(user.getId())
                        && f.getMovie().getId().equals(movieId))
                .findFirst()
                .ifPresent(favoriteRepository::delete);
        return "redirect:/movie/" + movieId;
    }

    /**
     * Display a paginated list of the current user's favourite movies.
     *
     * @param principal authenticated user
     * @param page      zero‑based page index
     * @param model     model used by Thymeleaf
     * @return the name of the favourites template
     */
    @GetMapping
    public String list(@AuthenticationPrincipal UserDetails principal,
                       @RequestParam(defaultValue = "0") int page,
                       Model model) {
        var user = userRepository.findByEmail(principal.getUsername()).orElseThrow();
        Page<Movie> favs = favoriteRepository.findFavorites(user.getId(), PageRequest.of(page, 20));
        model.addAttribute("movies", favs);
        return "favorites";
    }
}