package com.example.rest.domain.movie.repository;

import com.example.rest.domain.movie.models.SimilarMovie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimilarMovieRepository extends JpaRepository<SimilarMovie, Long> {

    boolean existsByTitle(String title);

    boolean existsByIMDbId(String IMDbId);

    SimilarMovie findByTitle(String title);

    SimilarMovie findByIMDbId(String IMDbId);
}
