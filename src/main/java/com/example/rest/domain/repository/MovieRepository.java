package com.example.rest.domain.repository;

import com.example.rest.domain.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    boolean existsByTitle(String title);
    Movie findByTitle(String title);
}
