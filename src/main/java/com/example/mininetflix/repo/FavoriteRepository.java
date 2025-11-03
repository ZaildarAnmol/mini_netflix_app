package com.example.mininetflix.repo;

import com.example.mininetflix.entity.Favorite;
import com.example.mininetflix.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository for persisting {@link Favorite} relationships and querying
 * favourite movies for a particular user.
 */
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    /**
     * Returns a page of movies that a given user has marked as favourites.
     *
     * @param userId   the id of the user
     * @param pageable pagination information
     * @return a page of movies favourited by the user
     */
    @Query("SELECT f.movie FROM Favorite f WHERE f.user.id = :userId")
    Page<Movie> findFavorites(@Param("userId") Long userId, Pageable pageable);
}