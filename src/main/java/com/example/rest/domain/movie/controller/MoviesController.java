package com.example.rest.domain.movie.controller;

import com.example.rest.domain.movie.models.Movie;
import com.example.rest.domain.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MoviesController {

    private final MovieService movieService;

    @GetMapping("")
    public ResponseEntity<Collection<Movie>> getMostPopularMovies() {
        return null;
    }

    @GetMapping("/{IMDbId}")
    public ResponseEntity<Movie> getMovieDetails(@PathVariable("IMDbId") String imDbId) throws IOException {
        Movie movie = movieService.getByIMDbId(imDbId); // TODO obsluzyc wyjatek
        return ResponseEntity.ok(movie);
    }
}
