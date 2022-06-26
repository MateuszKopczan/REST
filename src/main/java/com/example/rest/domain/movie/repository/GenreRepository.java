package com.example.rest.domain.movie.repository;

import com.example.rest.domain.movie.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    boolean existsByName(String name);

    Genre findByName(String name);
}
