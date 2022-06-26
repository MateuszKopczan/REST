package com.example.rest.domain.movie.repository;

import com.example.rest.domain.movie.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}
