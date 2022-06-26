package com.example.rest.domain.movie.repository;

import com.example.rest.domain.movie.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    boolean existsByTitle(String title);

    boolean existsByIMDbId(String IMDbId);

    Movie findByTitle(String title);

    Movie findByIMDbId(String IMDbId);
}
